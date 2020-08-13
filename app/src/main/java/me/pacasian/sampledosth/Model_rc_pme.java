package me.pacasian.sampledosth;


public class Model_rc_pme
{

    public String pf;
    public String upPme; 
    public String upRc;
    public Model_rc_pme(String pf, String upPme,String upRc)
    {
        this.upPme = upPme;
        this.pf = pf;
        this.upRc=upRc;

    }
    public String getPf() {
        return pf;
    }
    public String getPme() {
        return upPme;
    }
    public String getRc() {
        return upRc;
    }

}