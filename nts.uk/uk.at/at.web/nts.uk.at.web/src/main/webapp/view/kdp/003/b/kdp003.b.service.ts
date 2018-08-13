module nts.uk.at.view.kdp003.b {
    export module service {


        var servicePath = {
            findStampingOutputItemSet: "at/function/statement/findAll",
            addStampingOutputItemSet: "at/function/statement/add",
            updateStampingOutputItemSet: "at/function/statement/update",
            deleteStampingOutputItemSet: "at/function/statement/delete",
            getAllEnum: "at/function/statement/getAllEnum"
        }
        
        // Screen B
        export function getAllEnum(): JQueryPromise<any> {
            return nts.uk.request.ajax(servicePath.getAllEnum);
        }

        export function findAll(): JQueryPromise<Array<model.StampingOutputItemSetDto>> {
            return nts.uk.request.ajax(servicePath.findStampingOutputItemSet);

        }
        
        export function addStampingOutputItemSet(command: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.addStampingOutputItemSet, command);

        }
        
        export function updateStampingOutputItemSet(command: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.updateStampingOutputItemSet, command);

        }
        
        export function deleteStampingOutputItemSet(command: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.deleteStampingOutputItemSet, command);

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

