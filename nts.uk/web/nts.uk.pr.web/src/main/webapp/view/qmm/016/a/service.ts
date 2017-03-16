module nts.uk.pr.view.qmm016 {
    /**
     * Module service.
     */
    export module service {
        var path = {
            loadHistoryByUuid: 'pr/proto/wagetable/find/{0}'
        }
        
        /**
         * Service class.
         */
        export class Service extends base.simplehistory.service.BaseService<model.WageTable, model.WageTableHistory> {
            /**
             * Load history by uuid.
             */
            loadHistoryByUuid(uuid: string): JQueryPromise<model.WageTableHistoryModel> {
                return nts.uk.request.ajax(nts.uk.text.format(path.loadHistoryByUuid, uuid));
            }
        }
        
        /**
         * Instance.
         */
        export var instance: Service = new Service({
            historyMasterPath: 'pr/proto/wagetable/masterhistory',
            createHisotyPath: 'pr/proto/wagetable/history/create',
            deleteHistoryPath: 'pr/proto/wagetable/history/delete',
            updateHistoryStartPath: 'pr/proto/wagetable/history/update/start'
        });
    }
    
    /**
     * Model module.
     */
    export module model {
        /**
         * Wage table.
         */
        export interface WageTable extends base.simplehistory.model.MasterModel<WageTableHistory> {}
        
        /**
         * Wage table history model.
         */
        export interface WageTableHistory extends base.simplehistory.model.HistoryModel {}
        
        /**
         * Ement count type.
         */
        export interface DemensionElementCountType {
            code: number;
            name: string;
            isCertification: boolean;
            isAttendance;
        }
        
        /**
         * All Demession.
         */
        export var allDemension = [
            { code: 0, name: '一次元', isCertification: false, isAttendance: false },
            { code: 1, name: '二次元', isCertification: false, isAttendance: false },
            { code: 2, name: '三次元', isCertification: false, isAttendance: false },
            { code: 3, name: '資格', isCertification: true, isAttendance: false },
            { code: 4, name: '精皆勤手当', isCertification: false, isAttendance: true }
        ]

        /**
         * Normal demension.
         */
        export var normalDemension = _.filter(allDemension, (di) => {return !di.isCertification && !di.isAttendance});

        /**
         * Special demension.
         */
        export var specialDemension = _.filter(allDemension, (di) => {return di.isCertification || di.isAttendance});

        /**
         * Demension map.
         */
        export var demensionMap: {[index: number]: DemensionElementCountType} = new Array<>();
        _.forEach(allDemension, (de) => {
            demensionMap[de.code] = de;
        })

        /**
         * Wage table history.
         */
        export interface WageTableHistoryModel {
            /** The head. */
            head: WageTableHeadDto
            
            /** The history id. */
            historyId: string;
            
            /** The start month. */
            startMonth: number;
            
            /** The end month. */
            endMonth: number;
            
            /** The demension details. */
            //demensionDetails: Array<WageTableDemensionDetailDto>;
            
            /** The value items. */
            valueItems: Array<WageTableItemDto>;
        }
        
        /**
         * Wage table head dto.
         */
        export interface WageTableHeadDto {
            /** The code. */
            code: string;
            
            /** The name. */
            name: string;
            
            /** The demension set. */
            demensionSet: number;
            
            /** The memo. */
            memo: string
        }
        
        /**
         * Wage table item dto.
         */
        export interface WageTableItemDto {
            /** The element 1 id. */
            element1Id: string;
            
            /** The element 2 id. */
            element2Id: string;
            
            /** The element 3 id. */
            element3Id: string;
            
            /** The amount. */
            amount: number;
        }
}