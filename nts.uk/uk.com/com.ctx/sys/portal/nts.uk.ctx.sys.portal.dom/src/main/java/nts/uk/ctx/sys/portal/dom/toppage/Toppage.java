package nts.uk.ctx.sys.portal.dom.toppage;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNO;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページ.トップページ（New）.トップページ
 * @author LienPTK
 *
 */
@Getter
public class Toppage extends AggregateRoot {
	/** コード */
	private TopPageCode topPageCode;
	/** レイアウトの表示種類 */
	private LayoutDisplayType layoutDisp;
	/** 会社ID */
	private String cid;
	/** 名称 */
	private TopPageName topPageName;
	/** 枠レイアウト1 */
	private Optional<LayoutNO> frameLayout1;
	/** 枠レイアウト2 */
	private Optional<LayoutNO> frameLayout2;
	/** 枠レイアウト3 */
	private Optional<LayoutNO> frameLayout3;

	public Toppage() {}

	public static Toppage createFromMemento(MementoGetter memento) {
		Toppage domain = new Toppage();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.cid = memento.getCid();
		this.layoutDisp = LayoutDisplayType.valueOf(memento.getLayoutDisp().intValue());
		this.topPageCode = new TopPageCode(memento.getTopPageCode());
		this.topPageName = new TopPageName(memento.getTopPageName());
		this.frameLayout1 = Optional.ofNullable(memento.getFrameLayout1())
				.map(BigDecimal::valueOf).map(LayoutNO::new);
		this.frameLayout2 = Optional.ofNullable(memento.getFrameLayout2())
				.map(BigDecimal::valueOf).map(LayoutNO::new);
		this.frameLayout3 = Optional.ofNullable(memento.getFrameLayout3())
				.map(BigDecimal::valueOf).map(LayoutNO::new);
	}

	public void setMemento(MementoSetter memento) {
		memento.setCid(this.cid);
		memento.setLayoutDisp(BigDecimal.valueOf(this.layoutDisp.value));
		memento.setTopPageCode(this.topPageCode.v());
		memento.setTopPageName(this.topPageName.v());
		memento.setFrameLayout1(this.frameLayout1.map(LayoutNO::v).map(BigDecimal::intValue).orElse(null));
		memento.setFrameLayout2(this.frameLayout2.map(LayoutNO::v).map(BigDecimal::intValue).orElse(null));
		memento.setFrameLayout3(this.frameLayout3.map(LayoutNO::v).map(BigDecimal::intValue).orElse(null));
	}

	public static interface MementoSetter {
		public void setTopPageCode(String toppageCode);
		public void setLayoutDisp(BigDecimal layoutDisp);
		public void setCid(String cid);
		public void setTopPageName(String topPageName);
		public void setFrameLayout1(Integer frameLayout1);
		public void setFrameLayout2(Integer frameLayout2);
		public void setFrameLayout3(Integer frameLayout3);
	}


	public static interface MementoGetter {
		public String getTopPageCode();
		public BigDecimal getLayoutDisp();
		public String getCid();
		public String getTopPageName();
		public Integer getFrameLayout1();
		public Integer getFrameLayout2();
		public Integer getFrameLayout3();
	}
}
