package com.zzq.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> implements Serializable {
    private Integer totalNumber;
    private Integer number;
    private Integer size;
}
