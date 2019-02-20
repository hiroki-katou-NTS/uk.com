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
            findClassifySetting: 'ctx/at/schedule/shift/basicworkregister/classificationbasicwork/findSetting',
            
            // Find WorkTypeCode List
            findWorktypeCodeList: 'at/share/worktype/findSelectAble',
            
            // Find WorkTimeCode List
            findWorktimeCodeList: 'at/shared/worktimesetting/findAll'
            
        };

        /**
         * Find CompanyBasicWork
         */    
        export function findCompanyBasicWork(): JQueryPromise<model.CompanyBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findCompanyBasicWork);
        }
        
        /**
         * Save CompanyBasicWork
         */
        export function saveCompanyBasicWork(command: Array<model.BasicWorkSettingDto>): JQueryPromise<any> {
            var data = {basicWorkSetting: command};
            return nts.uk.request.ajax(paths.saveCompanyBasicWork, data);
        }
        
        /**
         * Find WorkplaceBasicWork
         */       
        export function findWorkplaceBasicWork(workplaceId: string): JQueryPromise<model.WorkplaceBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findWorkplaceBasicWork + "/" + workplaceId);
        }
        
        /**
         * Save WorkplaceBasicWork
         */
        export function saveWorkplaceBasicWork(command: model.WorkplaceBasicWorkDto): JQueryPromise<any> {
            let data = {workplaceId: command.workplaceId, basicWorkSetting: command.basicWorkSetting}
            return nts.uk.request.ajax(paths.saveWorkplaceBasicWork, data);
        }
        
        /**
         * Remove WorkplaceBasicWork
         */
        export function removeWorkplaceBasicWork(command: string): JQueryPromise<any> {
            let data = {workplaceId: command}
            return nts.uk.request.ajax(paths.removeWorkplaceBasicWork, data);
        }
        
        /**
         * Find WorkplaceSetting
         */
        export function findWorkplaceSetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorkplaceSetting);
        }
        
        /**
         * Find ClassifyBasicWork
         */
        export function findClassifyBasicWork(classifyCode: string): JQueryPromise<model.ClassifiBasicWorkFindDto> {
            return nts.uk.request.ajax(paths.findClassifyBasicWork + "/" + classifyCode);
        }
        
        /**
         * Save ClassifyBasicWork
         */
        export function saveClassifyBasicWork(command: model.ClassificationBasicWorkDto): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.saveClassifyBasicWork, command);
        }
        
        /**
         * Remove ClassifyBasicWork
         */
        export function removeClassifyBasicWork(command: string): JQueryPromise<any> {
            let data = {classificationCode: command}
            return nts.uk.request.ajax(paths.removeClassifyBasicWork, data);
        }
        
        /**
         * Find ClassifySetting
         */
        export function findClassifySetting(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findClassifySetting);
        }
        
        /**
         * Find WorktypeCode List
         */
        export function findWorktypeCodeList(command: Array<number>): JQueryPromise<any> {
            let data = {workStyleLst: command};
            return nts.uk.request.ajax(paths.findWorktypeCodeList, data);
        }
        
        /**
         * Find WorkTimeCode List
         */
        export function findWorktimeCodeList(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findWorktimeCodeList);
        }
        
        export function saveAsExcel(): JQueryPromise<any> {
            let program = nts.uk.ui._viewModel.kiban.programName().split(" ");
            let domainType = "KSM006";
            if (program.length > 1) {
                program.shift();
                domainType = domainType + program.join(" ");
            }
            return nts.uk.request.exportFile('/masterlist/report/print', 
                {domainId: 'BasicWorkRegister', domainType: domainType, 
                languageId: 'ja', reportType: 0});
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
