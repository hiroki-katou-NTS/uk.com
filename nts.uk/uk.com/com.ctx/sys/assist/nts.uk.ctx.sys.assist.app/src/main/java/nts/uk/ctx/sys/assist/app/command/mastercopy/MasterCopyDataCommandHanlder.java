/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.app.command.mastercopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.assist.dom.mastercopy.InitValueAuthManagementAdapter;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyCategory;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyData;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.CopyDataRepository;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class MasterCopyDataCommandHanlder extends AsyncCommandHandler<MasterCopyDataCommand> {

	/**
	 * Logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MasterCopyDataCommandHanlder.class);

	/** The Constant NUMBER_OF_SUCCESS. */
	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";

	/** The Constant NUMBER_OF_ERROR. */
	private static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";

	/** The Constant DEFAULT_VALUE. */
	private static final int DEFAULT_VALUE = 0;

	/** The Constant DATA_PREFIX. */
	private static final String DATA_PREFIX = "DATA_";

	/** The Constant MAX_ERROR_RECORD. */
	private static final int MAX_ERROR_RECORD = 5;
	
	/** The Constant AUTHORITY_CATEGORY. */
	private static final int AUTHORITY_CATEGORY = 1;

	/** The master copy data finder. */
	@Inject
	private MasterCopyDataRepository repository;

	/** The copy data repository. */
	@Inject
	private CopyDataRepository copyDataRepository;
	
	/** The init value auth. */
	@Inject
	private InitValueAuthManagementAdapter initValueAuth;

	@Override
	protected void handle(CommandHandlerContext<MasterCopyDataCommand> context) {

		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();

		// get company id
		String companyId = context.getCommand().getCompanyId();

		// get command
		MasterCopyDataCommand command = context.getCommand();

		// setup data object
		PerCopyDataCorrectProcessDto dto = new PerCopyDataCorrectProcessDto();
		dto.setEndTime(GeneralDateTime.now());
		dto.setStartTime(GeneralDateTime.now());
		dto.setExecutionState(ExecutionState.PROCESSING);

		// Creating list of errors
		List<ErrorContentDto> errorList = new ArrayList<>();

		dto.setErrors(errorList);
		dto.setWithError(WithError.NO_ERROR);

		int countSuccess = DEFAULT_VALUE;
		int countError = DEFAULT_VALUE;

		// Variable to count amount of list of error records sent to DB
		int errorRecordCount = DEFAULT_VALUE;

		setter.setData(NUMBER_OF_SUCCESS, countSuccess);
		setter.setData(NUMBER_OF_ERROR, DEFAULT_VALUE);

        Map<Integer, Integer> categoryCopyMethod = command.getMasterDataList().stream()
                .collect(Collectors.toMap(MasterCopyCategoryDto::getMasterCopyId, MasterCopyCategoryDto::getCopyMethod));

        new ArrayList<>(categoryCopyMethod.keySet());

        List<MasterCopyData> masterCopyDataList = repository.findByListCategoryNo(new ArrayList<>(categoryCopyMethod.keySet()));

		for (MasterCopyCategoryDto categoryDto : command.getMasterDataList()) {
			Optional<MasterCopyData> optMasterCopyData = masterCopyDataList.stream()
					.filter(item -> item.getCategoryNo().v().intValue() == categoryDto
							.getMasterCopyId().intValue())
					.findFirst();
			if (!optMasterCopyData.isPresent()
					|| CollectionUtil.isEmpty(optMasterCopyData.get().getTargetTables())) {
				ErrorContentDto errorContentDto = createErrorReport(categoryDto);
				// Add to error list (save to DB every 5 error records)
				if (errorList.size() >= MAX_ERROR_RECORD) {
					errorRecordCount++;
					setter.setData(DATA_PREFIX + errorRecordCount, dto);
					// Clear the list for the new batch of error record
					errorList.clear();
				}
				countError += 1;
				errorList.add(errorContentDto);
				setter.updateData(NUMBER_OF_ERROR, countError); // update
				if (errorList.size() == 1)
					dto.setWithError(WithError.WITH_ERROR); // if there is even
															// one error, output
															// it
			}
		}

		// 初期値コピー処理
		for (MasterCopyData masterCopyData : masterCopyDataList) {
			for (TargetTableInfo targetTableInfo : masterCopyData.getTargetTables()) {
				try {
					copyDataRepository.copy(companyId, targetTableInfo,
							categoryCopyMethod.get(masterCopyData.getCategoryNo().v()));
				} catch (Exception ex) {
					MasterCopyCategory copyCategory = repository
							.findCatByCategoryNo(masterCopyData.getCategoryNo().v());
					MasterCopyCategoryDto categoryDto = new MasterCopyCategoryDto();
					categoryDto.setCategoryName(copyCategory.getCategoryName().v());
					categoryDto.setOrder(copyCategory.getOrder().v());
					categoryDto.setSystemType(TextResource.localize(copyCategory.getSystemType().nameId));
					ErrorContentDto errorContentDto = createErrorReport(categoryDto);

					// Add to error list (save to DB every 5 error records)
					if (errorList.size() >= MAX_ERROR_RECORD) {
						errorRecordCount++;
						setter.setData(DATA_PREFIX + errorRecordCount, dto);
						// Clear the list for the new batch of error record
						errorList.clear();
					}
					countError += 1;
					errorList.add(errorContentDto);
					setter.updateData(NUMBER_OF_ERROR, countError); // update
					if (errorList.size() == 1)
						dto.setWithError(WithError.WITH_ERROR); // if there is
																// even one
																// error, output
																// it
					LOGGER.error(ex.getMessage());
				}
			}
			countSuccess++;
			setter.updateData(NUMBER_OF_SUCCESS, countSuccess);
			
			if (AUTHORITY_CATEGORY == masterCopyData.getCategoryNo().v()) {
				initValueAuth.initValueAuth(companyId);
			}
		}

		if (!errorList.isEmpty()) {
			errorRecordCount++;
			setter.setData(DATA_PREFIX + errorRecordCount, dto);
		}

		dto.setEndTime(GeneralDateTime.now());
		dto.setExecutionState(ExecutionState.DONE);
	}

    private ErrorContentDto createErrorReport(MasterCopyCategoryDto categoryDto) {
        ErrorContentDto errorContentDto = new ErrorContentDto();
        errorContentDto.setCategoryName(categoryDto.getCategoryName());
        errorContentDto.setOrder(categoryDto.getOrder());
        errorContentDto.setSystemType(categoryDto.getSystemType().toString());
        errorContentDto.setMessage(TextResource.localize("Msg_1146"));
        return errorContentDto;
    }

}
