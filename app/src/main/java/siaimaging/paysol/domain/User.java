package siaimaging.paysol.domain;

/**
 * Created by dna on 11/7/15.
 */
public class User {
    private Long mId;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPassword;
    private String mFaceDataPath;

    public User(String firstName, String email, String password){
        mFirstName = firstName;
        mEmail = email;
        mPassword = password;
    }
    public User(String csv,String seprator){
        String val[] = csv.split(seprator);
        mId = new Long(val[0]);
        mFirstName = val[1];
        mLastName = val[2];
        mEmail = val[3];
        mPassword = val[4];
    }
    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public String getFaceDataPath() {
        return mFaceDataPath;
    }

    public void setFaceDataPath(String mFaceDataPath) {
        this.mFaceDataPath = mFaceDataPath;
    }
    public String toCSVString(String seprator){
        String object = mId+seprator+
                mFirstName+seprator+
                mLastName+seprator+
                mEmail+seprator+
                mPassword+seprator;
        return object;
    }
}
