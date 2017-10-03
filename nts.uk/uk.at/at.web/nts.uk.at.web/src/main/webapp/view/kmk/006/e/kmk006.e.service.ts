module nts.uk.at.view.kmk006.e {

    import UnitAutoCalSettingDto = nts.uk.at.view.kmk006.a.service.model.UnitAutoCalSettingDto;

    export module service {
        var paths = {
            saveUnitAutoCal: "ctx/at/shared/ot/autocal/unit/save",
        }

        /**
        * save
        */
        export function saveUnitAutoCal(command: UnitAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveUnitAutoCal, command);
        }
        

    }
}