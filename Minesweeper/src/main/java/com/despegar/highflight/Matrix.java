/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.despegar.highflight;

import com.despegar.highflight.utils.Matrix2DCellPosition;

public interface Matrix<T> {

    T getCell(Matrix2DCellPosition position);

    void setCell(Matrix2DCellPosition position, T value);

    Integer getHeight();

    Integer getWidth();

    Integer getArea();

}
