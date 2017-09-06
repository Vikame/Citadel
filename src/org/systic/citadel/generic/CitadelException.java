package org.systic.citadel.generic;

public class CitadelException extends RuntimeException {

    public CitadelException(){
        super();
    }

    public CitadelException(String reason){
        super(reason);
    }

}
