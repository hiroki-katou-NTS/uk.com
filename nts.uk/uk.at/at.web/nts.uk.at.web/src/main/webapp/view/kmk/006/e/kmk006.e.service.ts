module nts.uk.at.view.kmk006.e {

    import UsageSettingDto = nts.uk.at.view.kmk006.a.service.model.UsageSettingDto;
    import UnitAutoCalSettingDto = nts.uk.at.view.kmk006.a.service.model.UnitAutoCalSettingDto;

    export module service {
        var paths = {
            saveUnitAutoCal: "ctx/at/schedule/shift/autocalunit/save",
            saveCompanySettingEstimate: "ctx/at/schedule/shift/estimate/usagesetting/save"
        }

        /**
        * save
        */
        export function saveUnitAutoCal(command: UnitAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveUnitAutoCal, command);
        }
        
        /**
         * call service save UsageSettingDto
         */
        export function saveCompanySettingEstimate(dto: UsageSettingDto) {
            return nts.uk.request.ajax('at', paths.saveCompanySettingEstimate, dto);
        }

    }
}