package com.wanda.epc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author LianYanFei
 * @version 1.0
 * @project iot-epc-module
 * @description 变配电采集vo
 * @date 2023/3/8 17:42:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricDataDto {

    private String sign;

    private String funcid;

    private String receivetime;

    private String data;
}
