package com.equivi.mailsy.web.controller;

import com.equivi.mailsy.dto.emailer.EmailCollector;
import com.equivi.mailsy.dto.emailer.EmailCollectorMessage;
import com.equivi.mailsy.dto.emailer.EmailCollectorStatusMessage;
import com.equivi.mailsy.dto.emailer.EmailCollectorUrlMessage;
import com.equivi.mailsy.service.emailcollector.EmailCollectorService;
import com.equivi.mailsy.service.emailcollector.EmailScanningService;
import com.equivi.mailsy.service.emailcollector.EmailScanningServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/main/emailcollector")
public class EmailCollectorController {
    private static final String EMAIL_COLLECTOR_PAGE = "emailCollectorPage";
    private static final String SESSION_CRAWLING = "sessionCrawling";
    
    @Autowired
    private EmailCollectorService emailCollectorService;

    @Autowired
    private EmailScanningService emailScanningService;
    
	@RequestMapping(value = "/new", method = RequestMethod.GET)
    public String loadNewPage(Model model) {
		model.addAttribute("collector", new EmailCollector());
		
        return EMAIL_COLLECTOR_PAGE;
    }
    
    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    public String collectEmails(@ModelAttribute("collector") EmailCollector collector, Model map) throws Exception {
    	map.addAttribute("resultList", getEmailCollectorResults(collector));
    	
    	return EMAIL_COLLECTOR_PAGE;
    }
    
    private List<EmailCollector> getEmailCollectorResults(EmailCollector collector) throws Exception {
        List<EmailCollector> emailCollectorsList = Lists.newArrayList();
        return emailCollectorsList;
    }
    
    @RequestMapping(value = "async/begin", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseStatus(value = HttpStatus.OK)
    public void start(@RequestBody EmailCollector emailCollector, HttpServletRequest request) throws Exception {
    	emailCollectorService.subscribe(emailCollector.getSite());
        emailScanningService.subscribe();

        request.getSession().setAttribute(SESSION_CRAWLING, Boolean.TRUE);

    }
    
    @RequestMapping(value = "async/update", method = RequestMethod.GET)
    public @ResponseBody DeferredResult<EmailCollectorMessage> getUpdate() {
    	final DeferredResult<EmailCollectorMessage> result = new DeferredResult<>();
    	emailCollectorService.getUpdate(result);
        return result;
    	
    }

    @RequestMapping(value = "async/updateUrlScanning", method = RequestMethod.GET)
    public @ResponseBody DeferredResult<EmailCollectorUrlMessage> getUpdateUrlScanning(HttpServletRequest request) {
        Boolean crawlingStatus = (Boolean) request.getSession().getAttribute(SESSION_CRAWLING);
        final DeferredResult<EmailCollectorUrlMessage> result = new DeferredResult<>();

        if(crawlingStatus) {
            emailScanningService.getUrlScanningUpdate(result);
        } else {
            EmailScanningServiceImpl.resultUrlQueue.clear();

            EmailCollectorUrlMessage urlMessage = new EmailCollectorUrlMessage("FINISH");
            result.setResult(urlMessage);
        }

        return result;
    }
    
    @RequestMapping(value = "updateCrawlingStatus", method = RequestMethod.GET)
    public @ResponseBody EmailCollectorStatusMessage getUpdateCrawlingStatus(HttpServletRequest request) {
        boolean crawlingStatus = emailCollectorService.getUpdateCrawlingStatus();

        if(crawlingStatus) {
            request.getSession().setAttribute(SESSION_CRAWLING, Boolean.FALSE);
        }

        return new EmailCollectorStatusMessage(crawlingStatus);
    }
}
