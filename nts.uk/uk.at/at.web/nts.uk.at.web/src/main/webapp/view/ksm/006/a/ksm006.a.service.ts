module nts.uk.at.view.ksm006.a {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            // Company
            findCompanyBasicWork: 'ctx/at/schedule/shift/basicworkregister/companybasicwork/find',
            saveCompanyBasicWork: 'ctx/at/schedule/shift/basicworkregister/companybasicwork/save',
            
            //Workplace
            findWorkplaceBasicWork: 'ctx/at/schedule/shift/basicworkregister/workplacebasicwork/find',
            saveWorkplaceBasicWork: 'ctx/at/schedule/shift/basicworkregister/workplacebasicwork/save',
            removeWorkplaceBasicWork: 'ctx/at/schedule/shift/basicworkregister/workplacebasicwork/remove',
            findWorkplaceSetting: 'ctx/at/schedule/shift/basicworkregister/workplacebasicwork/findSetting',
            
            // Classification
            findClassifyBasicWork: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/find',
            saveClassifyBasicWork: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/save',
            removeClassifyBasicWork: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/remove',
            findClassifySetting: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/findSetting'
            
        };

// Company        
        export function findCompanyBasicWork(): JQueryPromise<model.CompanyBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findCompanyBasicWork);
        }
        
        export function saveCompanyBasicWork(command: Array<model.BasicWorkSettingDto>): JQueryPromise<any> {
            var data = {basicWorkSetting: command};
            return nts.uk.request.ajax(paths.saveCompanyBasicWork, data);
        }
        
// Workplace        
        export function findWorkplaceBasicWork(workplaceId: string): JQueryPromise<model.WorkplaceBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findWorkplaceBasicWork + "/" + workplaceId);
        }
        
        export function saveWorkplaceBasicWork(command: model.WorkplaceBasicWorkDto): JQueryPromise<any> {
            let data = {workplaceId: command.workplaceId, basicWorkSetting: command.basicWorkSetting}
            return nts.uk.request.ajax(paths.saveWorkplaceBasicWork, data);
        }
        
        export function removeWorkplaceBasicWork(command: string): JQueryPromise<any> {
            let data = {workplaceId: command}
            return nts.uk.request.ajax(paths.removeWorkplaceBasicWork, data);
        }
        
        export function findWorkplaceSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkplaceSetting);
        }
        
// Classification
        export function findClassifyBasicWork(classifyCode: string): JQueryPromise<model.ClassifiBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findClassifyBasicWork + "/" + classifyCode);
        }
        
         export function saveClassifyBasicWork(command: model.ClassificationBasicWorkDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveClassifyBasicWork, command);
        }
        
        export function removeClassifyBasicWork(command: string): JQueryPromise<any> {
            let data = {classificationCode: command}
            return nts.uk.request.ajax(paths.removeClassifyBasicWork, data);
        }
        
        export function findClassifySetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findClassifySetting);
        }
        
        

        /**
        * Model namespace.
        */
        export module model {
            
            /**
             * Interface CompanyBasicWorkFindDto
             */
            export interface CompanyBasicWorkFindDto {
                companyId: string;
                basicWorkSetting: Array<BasicWorkSettingFindDto>;
            }

            /**
             * Class BasicWorkSettingFindDto
             */
            export class BasicWorkSettingFindDto {
                workTypeCode: string;
                workingCode: string;
                workDayDivision: number;
                workTypeDisplayName: string;
                workingDisplayName: string;
            }

            /**
             * Class BasicWorkSettingDto
             */
            export class BasicWorkSettingDto {
                workTypeCode: string;
                siftCode: string;
                workDayDivision: number;

                constructor(workDayDivision: number, workTypeCode: string, siftCode: string) {
                    this.workDayDivision = workDayDivision;
                    this.workTypeCode = workTypeCode;
                    this.siftCode = siftCode;
                }
            }

            /**
             * Class WorkplaceBasicWorkFindDto
             */
            export class WorkplaceBasicWorkFindDto {
                workplaceId: string;
                basicWorkSetting: Array<BasicWorkSettingFindDto>;
            }

            /**
             * Class WorkplaceBasicWorkDto
             */
            export class WorkplaceBasicWorkDto {
                workplaceId: string;
                basicWorkSetting: Array<BasicWorkSettingDto>;
            }

            /**
             * Class ClassifiBasicWorkFindDto
             */
            export class ClassifiBasicWorkFindDto {
                companyId: string;
                classificationCode: string;
                basicWorkSetting: Array<BasicWorkSettingFindDto>;
            }

            /**
             * Class ClassificationBasicWorkDto
             */
            export class ClassificationBasicWorkDto {
                classificationCode: string;
                basicWorkSetting: Array<BasicWorkSettingDto>;
            }
           
        }
    }
}
