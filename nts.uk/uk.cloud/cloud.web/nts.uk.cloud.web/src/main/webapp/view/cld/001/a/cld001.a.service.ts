module nts.uk.cloud.view.cld001.a {

    export module service {
        var servicePath: any = {
            regist: "ctx/cld/operate/tenant/regist",
            generatePassword: "ctx/cld/operate/tenant/generatePassword"
        }
        export function regist(command: any): JQueryPromise<void> {
            return nts.uk.request.ajax(servicePath.regist, command);
        }

        export function generatePassword(): JQueryPromise<Array<model.generatePasswordDto>> {
            return nts.uk.request.ajax(servicePath.generatePassword);
        }

        export module model {
            export interface generatePasswordDto {
                password: string;
            }
        }
    }
}