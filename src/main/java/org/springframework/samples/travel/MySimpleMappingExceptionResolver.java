package org.springframework.samples.travel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class MySimpleMappingExceptionResolver  extends SimpleMappingExceptionResolver {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public String buildLogMessage(Exception ex, HttpServletRequest request) {
        return "Spring MVC exception: " + ex.getLocalizedMessage();
    }
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String viewName=determineViewName(ex, request);
		logger.info("viewName:"+viewName);
		if (viewName!=null) {
			Integer statusCode = super.determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			request.setAttribute("error", ex.getMessage());
			logger.error(ex.getMessage());
			return getModelAndView(viewName, ex, request);
		}
		return null;

	}

}
