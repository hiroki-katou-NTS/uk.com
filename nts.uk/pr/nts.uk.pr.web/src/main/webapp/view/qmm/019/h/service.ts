module qmm019.h.service {
    var paths = {
        getLayoutInfor: "pr/proto/personalwage/findPersonalWageName"
    }
    
    export function getPersonalWageNames(): JQueryPromise<Array<model.PersonalWageNameDto>> {
        var dfd = $.Deferred<Array<any>>();
        nts.uk.request.ajax(paths.getLayoutInfor)
            .done(function(res: Array<any>) {
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise();
    }
    
    export module model {
        export interface LayoutMasterModel {
            personalWageName: String;
            companyCode: String;
        }
    }
    
     export module model {
            // layout
            export class PersonalWageNameDto {
             companyCode : string;
             categoryAtr : number;
             personalWageCode : string;
             personalWageName : string;
            }

        }
}