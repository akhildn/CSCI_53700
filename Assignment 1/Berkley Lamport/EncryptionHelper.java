
public class EncryptionHelper {
	
	public static final int KEY = 10;
	
	
	/** method to encrypt logical clock values
	 * @param plainLogicalClock
	 * @return encrypted logical clock value
	 */
	public static int encryptMessage(int plainLogicalClock)  {
		return plainLogicalClock - KEY;
	}
	
	
	/**  method to decrypt the encrypted logical clock values
	 * @param encryptedLogicalClock
	 * @return decrypted logical clock
	 */
	public static int decryptMessage(int encryptedLogicalClock)  {
		
		return encryptedLogicalClock + KEY;
        
	}
}
