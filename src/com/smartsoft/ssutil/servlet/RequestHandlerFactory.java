package com.smartsoft.ssutil.servlet;

public interface RequestHandlerFactory {
    
   <T extends RequestHandler>  T createRequestHandler(Class<T> cls);
    
}
