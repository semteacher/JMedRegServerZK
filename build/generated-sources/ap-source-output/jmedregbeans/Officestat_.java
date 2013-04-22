package jmedregbeans;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jmedregbeans.Medoffice;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2013-04-22T16:53:56")
@StaticMetamodel(Officestat.class)
public class Officestat_ { 

    public static volatile SingularAttribute<Officestat, Integer> id;
    public static volatile SingularAttribute<Officestat, Medoffice> officeId;
    public static volatile SingularAttribute<Officestat, String> staffFio;
    public static volatile SingularAttribute<Officestat, Integer> patientTotal;
    public static volatile SingularAttribute<Officestat, Date> statDate;
    public static volatile SingularAttribute<Officestat, String> updRecord;

}