package tdmubeans;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.0.v20110604-r9504", date="2012-03-03T14:33:23")
@StaticMetamodel(TblLogin.class)
public class TblLogin_ { 

    public static volatile SingularAttribute<TblLogin, Integer> loginId;
    public static volatile SingularAttribute<TblLogin, String> userId;
    public static volatile SingularAttribute<TblLogin, String> login;
    public static volatile SingularAttribute<TblLogin, Integer> userAccess;
    public static volatile SingularAttribute<TblLogin, String> pass;

}