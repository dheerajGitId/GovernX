package com.clientdata.schemas.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum PolicyName {

    HEALTH_INSURANCE_POLICY(List.of(
            Approvers.ALASTAIR_COOK,
            Approvers.JAMES_ANDERSON,
            Approvers.STUART_BROAD,
            Approvers.GARY_KIRSTEN
    )),

    AUTO_INSURANCE_POLICY(List.of(
            Approvers.KUMAR_SANGAKKARA,
            Approvers.MITHALI_RAJ,
            Approvers.SHIVAM_DUBE,
            Approvers.SACHIN_TENDULKAR
    )),

    HOME_INSURANCE_POLICY(List.of(
            Approvers.SOURAV_GANGULY,
            Approvers.KAPIL_DEV,
            Approvers.CLIVE_LLOYD,
            Approvers.IMRAN_KHAN
    )),

    LIFE_INSURANCE_POLICY(List.of(
            Approvers.MALCOM_MARSHALL,
            Approvers.JOEL_GARNER,
            Approvers.VIVIAN_RICHARDS,
            Approvers.ASHOK_DINDA
    )),

    TRAVEL_INSURANCE_POLICY(List.of(
            Approvers.KAVIYA_MARAN,
            Approvers.BRENDON_MCCULLUM,
            Approvers.TIM_SOUTHEE,
            Approvers.NEIL_WAGNER
    )),

    BUSINESS_INSURANCE_POLICY(List.of(
            Approvers.TRENT_BOULT,
            Approvers.MITCHELL_JOHNSON,
            Approvers.GLENN_MCGRATH,
            Approvers.BRETT_LEE
    )),

    CYBER_INSURANCE_POLICY(List.of(
            Approvers.RAHUL_DRAVID,
            Approvers.RAVI_SHASTRI,
            Approvers.MAHELA_JAYAWARDENE,
            Approvers.SHAKIB_AL_HASAN
    )),

    PET_INSURANCE_POLICY(List.of(
            Approvers.MARK_BOUCHER,
            Approvers.SHAUN_POLLOCK,
            Approvers.GREAME_SMITH,
            Approvers.JACQUES_KALLIS
    )),

    RENTAL_INSURANCE_POLICY(List.of(
            Approvers.DALE_STEYN,
            Approvers.VERNON_PHILANDER,
            Approvers.MORNE_MORKEL,
            Approvers.IMRAN_TAHIR
    ));

    private final List<Approvers> approvers;

    PolicyName(List<Approvers> approvers) {
        this.approvers = approvers;
    }
}