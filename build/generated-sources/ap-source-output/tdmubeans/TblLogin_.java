package tdmubeans;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2014-05-22T18:59:15")
@StaticMetamodel(TblLogin.class)
public class TblLogin_ { 

    public static volatile SingularAttribute<TblLogin, Integer> loginId;
    public static volatile SingularAttribute<TblLogin, String> userId;
    public static volatile SingularAttribute<TblLogin, String> login;
    public static volatile SingularAttribute<TblLogin, Integer> userAccess;
    public static volatile SingularAttribute<TblLogin, String> pass;

}