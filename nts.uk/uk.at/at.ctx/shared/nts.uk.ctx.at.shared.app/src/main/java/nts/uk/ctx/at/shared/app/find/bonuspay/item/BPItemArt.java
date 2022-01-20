package nts.uk.ctx.at.shared.app.find.bonuspay.item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@AllArgsConstructor
public class BPItemArt {

	public List<Boolean> lstUseArt;

	public List<Boolean> lstUseSpecArt;
}
