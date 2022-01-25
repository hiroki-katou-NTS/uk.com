package nts.uk.ctx.at.shared.app.find.bonuspay.item;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BPItemArt {
	public List<Boolean> lstUseArt;
	public List<Boolean> lstUseSpecArt;
}