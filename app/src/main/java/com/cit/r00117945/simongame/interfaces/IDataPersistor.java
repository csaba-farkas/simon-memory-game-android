package com.cit.r00117945.simongame.interfaces;

public interface IDataPersistor {

    /**
     * <p>This interface includes the signatures of the methods which must
     * be implemented by any class which implements this interface.</p>
     * <p>Date of last modification: 27/11/2015</p>
     *
     * @author Csaba Farkas csaba.farkas@mycit.ie
     */
    public void write(Integer highScore);

    public Integer read();
}
