package com.smartsoft.ssutil;

public class AssertionException extends RuntimeException{

    private Object sceneOfTheCrime;

    public AssertionException() {
    }

    public AssertionException(String message) {
        super(message);
    }

//    public AssertionException(String message, Throwable cause) {
//        super(message, cause);
//    }

    public AssertionException(String message, Object sceneOfTheCrime) {
        super(message );
        this.sceneOfTheCrime = sceneOfTheCrime;
    }

    public AssertionException(Throwable cause) {
        super(cause);
    }

    public Object getSceneOfTheCrime() {
        return sceneOfTheCrime;
    }

    public String getMessage() {
        String m = super.getMessage();
        m = StringUtil.normalize(m);
        return (m==null?"":m) + (sceneOfTheCrime!=null?"  SceneOfTheCrime: " + sceneOfTheCrime:"");
    }
}
