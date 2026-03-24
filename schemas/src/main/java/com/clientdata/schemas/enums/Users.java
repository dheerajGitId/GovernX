package com.clientdata.schemas.enums;

import lombok.Getter;

@Getter
public enum Users {

    ALASTAIR_COOK(Nationality.BRITISH),
    ANDREW_FLINTOFF(Nationality.BRITISH),
    BEN_STOKES(Nationality.BRITISH),
    ISSY_WONG(Nationality.BRITISH),
    JAMES_ANDERSON(Nationality.BRITISH),
    JASON_ROY(Nationality.BRITISH),
    JOE_ROOT(Nationality.BRITISH),
    LAUREN_BELL(Nationality.BRITISH),
    SARAH_TAYLOR(Nationality.BRITISH),
    STUART_BROAD(Nationality.BRITISH),

    BRETT_LEE(Nationality.AUSTRALIAN),
    DAVID_WARNER(Nationality.AUSTRALIAN),
    JASON_BEHRENDORFF(Nationality.AUSTRALIAN),
    JASON_GILLESPIE(Nationality.AUSTRALIAN),
    JOSH_HAZLEWOOD(Nationality.AUSTRALIAN),
    MARNUS_LABUSCHAGNE(Nationality.AUSTRALIAN),
    MITCHELL_JOHNSON(Nationality.AUSTRALIAN),
    STEVE_SMITH(Nationality.AUSTRALIAN),
    USMAN_KHAWAJA(Nationality.AUSTRALIAN),

    RAHUL_DRAVID(Nationality.INDIAN),
    RAVICHANDRAN_ASHWIN(Nationality.INDIAN),
    RAVINDRA_JADEJA(Nationality.INDIAN),
    SHIVAM_DUBE(Nationality.INDIAN),

    DEWALD_BREVIS(Nationality.SOUTH_AFRICAN),
    LUNGI_NGIDI(Nationality.SOUTH_AFRICAN),
    TRISTAN_STUBBS(Nationality.SOUTH_AFRICAN),
    JASON_SMITH(Nationality.SOUTH_AFRICAN),

    KANE_WILLIAMSON(Nationality.NEW_ZEALANDER),

    RASHID_KHAN(Nationality.AFGHAN),
    MOHAMMAD_NABI(Nationality.AFGHAN),
    ASGHAR_AFGHANI(Nationality.AFGHAN),

    KUMAR_SANGAKKARA(Nationality.SRI_LANKAN),
    KUSAL_MENDIS(Nationality.SRI_LANKAN),
    NIROSHAN_DICKWELLA(Nationality.SRI_LANKAN),
    CHAMIKA_KARUNARATNE(Nationality.SRI_LANKAN),

    CHRIS_GAYLE(Nationality.CARIBBEAN),
    JASON_HOLDER(Nationality.CARIBBEAN),
    KIERON_POLLARD(Nationality.CARIBBEAN),

    ALI_KHAN(Nationality.AMERICAN),
    MONANK_PATEL(Nationality.AMERICAN),
    COREY_ANDERSON(Nationality.AMERICAN),
    SAURABH_NETRAVALKAR(Nationality.AMERICAN),


    BABAR_AZAM(Nationality.PAKISTANI),
    MOHAMMAD_RIZWAN(Nationality.PAKISTANI),
    SHAHEEN_AFRIDI(Nationality.PAKISTANI),

    SHAKIB_AL_HASAN(Nationality.BANGLADESHI),
    MUSHFIQUR_RAHIM(Nationality.BANGLADESHI),
    LITTON_DAS(Nationality.BANGLADESHI),

    GOVERN_X(Nationality.OTHER),
    OTHERS(Nationality.OTHER);

    private final Nationality nationality;

    Users(Nationality nationality) {
        this.nationality = nationality;
    }

}