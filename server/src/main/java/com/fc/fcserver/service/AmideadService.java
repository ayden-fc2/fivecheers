package com.fc.fcserver.service;

import com.fc.fcserver.entity.V2AmideadSt;

public interface AmideadService {
    V2AmideadSt getAmidead();

    boolean updateTime();

    String updateGiftStill(int secret);

    V2AmideadSt getAmideadAll();

    boolean updateAmideadAll(V2AmideadSt amideadSt);
}
