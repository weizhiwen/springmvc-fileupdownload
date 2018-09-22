package com.wenshixin.interceptor;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: wzw
 * Date: 2018/9/21
 * Time: 0:07
 * To change this template use File | Settings | File Templates.
 * Description:
 */
public class FileUploadInterceptor implements HandlerInterceptor {
    private long maxSize;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//        System.out.println("拦截到了请求");
        if (httpServletRequest != null && ServletFileUpload.isMultipartContent(httpServletRequest)) {
            ServletRequestContext servletRequestContext = new ServletRequestContext(httpServletRequest);
            long requestSize = servletRequestContext.contentLength();
            if (requestSize > maxSize) {
                // 抛出异常
                throw new MaxUploadSizeExceededException(maxSize);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}
