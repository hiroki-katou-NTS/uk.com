package nts.uk.ctx.at.function.app.nrl;

/**
 * Default value.
 * 
 * @author manhnd
 */
public class DefaultValue {

	public static final String SOH = "3b";
	public static final String HDR_ACK = "01";
	public static final String HDR_NAK = "02";
	public static final String LENGTH_NAK = "0020";
	public static final String VERSION = "01";
	public static final String FLAG_CONT_NOACK = "20";
	public static final String FLAG_CONT_ACK = "a0";
	public static final String FLAG_END_NOACK = "40";
	public static final String FLAG_END_ACK = "c0";
	public static final String NO_FRAG = "0000";
	public static final String ZERO_PADDING = "00000000";
	public static final String INIT_BCC = "0000";
	public static final String PRELIMINARY = "000000000000";
	public static final String SPACE4 = "    ";
	public static final String SPACE5 = "     ";
	public static final String SPACE6 = "      ";
	
	public static final int ALL_IO_PLS = 949; 
	public static final int ALL_IO_PKT_LEN_XPL = 32;
	public static final int ALL_PETITIONS_PLS = 949;
	public static final int ALL_PETITIONS_PKT_LEN_XPL = 32;
	public static final int SINGLE_FRAME_LEN = 64;
	public static final int SINGLE_FRAME_LEN_32 = 32;
	public static final int SINGLE_FRAME_LEN_48 = 48;
	public static final int SINGLE_FRAME_LEN_80 = 80;
	
	public static final String HEAD_TYPE = "head";
	public static final String DATA_TYPE = "data";
}
