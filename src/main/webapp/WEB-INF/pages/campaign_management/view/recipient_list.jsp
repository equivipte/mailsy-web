<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="page-header">
    <h1>
        <spring:message code="label.campaign.email.recipients"/>
    </h1>
</div>


<c:set var="sendDate"><spring:message code="label.campaign.scheduled_send_date"/></c:set>

</br>

<div class="table-responsive">
<table id="table-subscriber" class="table table-striped table-bordered table-hover">
<thead>
<tr>
    <th><spring:message code="label.campaign.email.recipients"/></th>
</tr>
</thead>
<c:if test="${recipientList.size() > 0 }">
    <tbody>
    <c:forEach items="${recipientList}" var="recipient">
        <tr id="${recipient}">
            <td>${recipient}</td>
        </tr>
    </c:forEach>
    </tbody>
    </table>
    </div>
</c:if>