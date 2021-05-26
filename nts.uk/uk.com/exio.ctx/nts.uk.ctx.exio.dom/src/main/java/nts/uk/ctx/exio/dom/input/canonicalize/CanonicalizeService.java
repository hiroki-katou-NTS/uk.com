package nts.uk.ctx.exio.dom.input.canonicalize;
/**
 * 正準化サービス
 * @author ai_muto
 */
public class CanonicalizeService {
	public void canonicalize(Require require) {
		//TODO:
	}

	public interface Require{
		void save(CanonicalizedData data);
	}
}
