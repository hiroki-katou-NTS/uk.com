module nts.uk.at.view.kdp003.b {
    export module service {


        var servicePath = {
            findStampingOutputItemSet: "at/function/statement/findAll"
        }

        export function findAll(): JQueryPromise<Array<model.StampingOutputItemSetDto>> {
            return nts.uk.request.ajax(servicePath.findStampingOutputItemSet);

        }

        export module model {
            export interface StampingOutputItemSetDto {
                stampOutputSetCode: string;
                stampOutputSetName: string;
                outputEmbossMethod: boolean;
                outputWorkHours: boolean
                outputSetLocation: boolean;
                outputPosInfor: boolean;
                outputOT: boolean;
                outputNightTime: boolean;
                outputSupportCard: boolean;
            }
           

        }
    }
}

