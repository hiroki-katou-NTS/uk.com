module nts.uk.at.view.kmk006.e {

    export module service {
        var paths = {
            findEnumUnitAutoCal: "ctx/at/shared/ot/autocal/unit/find/autocalunit",
            saveUnitAutoCal: "ctx/at/shared/ot/autocal/unit/save",
        }

        export function getUseUnitAutoCal(): JQueryPromise<model.UnitAutoCalSettingDto> {
            return nts.uk.request.ajax("at", paths.findEnumUnitAutoCal);
        }
        
        /**
        * save
        */
        export function saveUnitAutoCal(command: model.UnitAutoCalSettingDto): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.saveUnitAutoCal, command);
        }

        export module model {
            export interface UnitAutoCalSettingDto {
                useWkpSet: boolean;
                useJobSet: boolean;
                useJobwkpSet: boolean;
            }
        }
    }
}