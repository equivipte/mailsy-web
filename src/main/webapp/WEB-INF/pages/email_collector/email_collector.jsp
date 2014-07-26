<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
		paceOptions = {  
		  // Configuration goes here. Example:  
		  elements: false,  
		  restartOnPushState: false,  
		  restartOnRequestAfter: false  
		};  	
</script>

<c:set var="context" value="${pageContext.request.contextPath}"/>

<div class="page-header">
    <h1>
        <spring:message code="label.menu.email.collector"/>
    </h1>
</div>

<div id="dialog-message" class="hide">
    <p>
        Nothing to select
    </p>
</div>

<div id="progress" class="hide">
    <div class="spinner">
      <div class="rect1"></div>
      <div class="rect2"></div>
      <div class="rect3"></div>
      <div class="rect4"></div>
      <div class="rect5"></div>
    </div>

    <span class="scanning">Scanning: <span id="urltext"></span></span>

</div>

<div class="widget-main no-padding">
	<table>
	    <td>
	        <div>
	        	<spring:message code="label.please.type.site.search" var="siteSearch"/>
	        	<input id="url" path="site" size="100" maxlength="255" placeholder="${siteSearch}"/>
	        </div>
	    </td>
	    <td>
           <div id="crawlingsearch">
           		<button id="crawling" class="btn btn-sm btn-info">
	           		<i class="icon-search white bigger-120 crawlingsearch"></i>
	            	<spring:message code="label.search"/>
            	</button>
           </div>
           <div id="crawlingcancel" class="hide">
           		<button id="cancelcrawling" class="btn btn-sm btn-info">
           			<spring:message code="label.cancel"/>
           		</button>
           </div>
	    </td>
	</table>
</div>
&nbsp;
&nbsp;
&nbsp;
<div id="table_result" class="table-responsive">
    <table id="emailTable" class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
            <th><spring:message code="label.common.emailaddress"/></th>
        </tr>
        </thead>
        
        <tbody>
        </tbody>
    </table>
</div>

<div class="pull-left">
    <a href="#">
        <button class="btn btn-info" id="export-btn">
            <i class="icon-mail-forward white bigger-120"></i>
            <spring:message code="label.common.export_results_to_excel"/>
        </button>
    </a>

    <a href="#">
        <button class="btn btn-info" id="print-receipt-btn">
            <i class="icon-ok white bigger-120"></i>
            <spring:message code="label.common.verify_email_list"/>
        </button>
    </a>
</div>
</br>
</br>
</br>
<style>
    div#sb-container {
        z-index: 10000;
    }
</style>

<script type="text/javascript" src="<c:url value='/resources/js/jquery-1.9.1.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jquery-blink.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/crawlingpolling.js' /> "></script>
<link href="<c:url value="/resources/css/crawlingpolling.css" />" rel="stylesheet">

<script type="text/javascript">
	$(document).ready(function() {
		
		$("#crawling").click(function(){
			$("#progress").removeClass("hide");
			$("#progress").addClass("show");
			$("#url").prop('disabled', true);
			$("#crawlingsearch").toggleClass("hide");
			$("#crawlingcancel").toggleClass("hide");
			
			$('.blink').blink();
			
			$("#emailTable").find("tr:gt(0)").remove();
			
			var url = $("#url").val();
			
			var startUrl = "${context}/main/emailcollector/async/begin"; 
			var pollUrl = "${context}/main/emailcollector/async/update";
			var crawlingStatusUrl = "${context}/main/emailcollector/updateCrawlingStatus";
			var poll = new Poll();
			poll.start(startUrl,pollUrl, url, crawlingStatusUrl);
		}); 
	});
</script>