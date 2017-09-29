module nts.uk.at.view.kmk006.e {

    import UnitAutoCalSettingDto = nts.uk.at.view.kmk006.a.service.model.UnitAutoCalSettingDto;

    export module service {
        var paths = {
            saveUnitAutoCal: "ctx/at/schedule/shift/autocalunit/save",
        }

        /**
        * save
        */
        export function saveUnitAutoCal(command: UnitAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveUnitAutoCal, command);
        }
        

    }
}