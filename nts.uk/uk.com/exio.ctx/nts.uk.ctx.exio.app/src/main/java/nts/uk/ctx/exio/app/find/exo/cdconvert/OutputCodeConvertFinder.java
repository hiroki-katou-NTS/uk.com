package nts.uk.ctx.exio.app.find.exo.cdconvert;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.cdconvert.OutputCodeConvertRepository;

/*
 * 出力コード変換
 */
@Stateless
public class OutputCodeConvertFinder {

	@Inject
	private OutputCodeConvertRepository finder;

	public List<OutputCodeConvertDto> getOutputCodeConvertByCid(String cid) {
		return finder.getOutputCodeConvertByCid(cid).stream().map(item -> OutputCodeConvertDto.fromDomain(item))
				.collect(Collectors.toList());
	}
}
