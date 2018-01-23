module nts.uk.at.view.kal004.a.service {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    import share = nts.uk.at.view.kal004.share.model;
        var paths = {
            getAlarmPattern: "at/function/alarm/pattern/setting",
            getCheckConditionCode: "at/function/alarm/check/condition/code",
            addAlarmPattern: "at/function/alarm/add/pattern/setting",
            updateAlarmPattern: "at/function/alarm/update/pattern/setting",
            removeAlarmPattern: "at/function/alarm/remove/pattern/setting",
        }
        
        
        export function getAlarmPattern(): JQueryPromise<Array<share.AlarmPatternSettingDto>> {
            let alarmPermissionSettingDto ={
                authSetting: true,
                roleIds: ['0001', '0002']    
            }
            let extractionDailyDto= {
                extractionId: "",
                extractionRange: 0,
                strSpecify: 0,
                strPreviousDay: 0,
                strMakeToDay: 0,
                strDay: 3,
                strPreviousMonth: null,
                strCurrentMonth: null,
                strMonth: null,
                endSpecify: 0,
                endPreviousDay: 0,
                endMakeToDay: 0,
                endDay: 1,
                endPreviousMonth: null,
                endCurrentMonth: null,
                endMonth: null
            }
            let checkConditionDto1={
                alarmCategory: 1, 
                checkConditionCodes: ['001', '002'],
                extractionDailyDto:    extractionDailyDto 
            }
            let checkConditionDto2={
                alarmCategory: 2, 
                checkConditionCodes: ['003', '004', '005'],
                 extractionDailyDto:    extractionDailyDto     
            }
            let checkConditionDto3={
                alarmCategory: 3, 
                checkConditionCodes: ['006', '007', '008'] ,
                extractionDailyDto:    extractionDailyDto    
            }                          
            let alarmPatternSettingDto1 ={
                alarmPatternCD : '01',
                alarmPatternName: 'name01',
                alarmPerSet : alarmPermissionSettingDto,
                checkConList : [checkConditionDto1]    
            }
            let alarmPatternSettingDto2 ={
                alarmPatternCD : '02',
                alarmPatternName: 'name02',
                alarmPerSet : alarmPermissionSettingDto,
                checkConList : [checkConditionDto2]    
            }
            let alarmPatternSettingDto3 ={
                alarmPatternCD : '03',
                alarmPatternName: 'name03',
                alarmPerSet : alarmPermissionSettingDto,
                checkConList : [checkConditionDto1, checkConditionDto2, checkConditionDto3]    
            }            
//            let dfd = $.Deferred();
//            let alarmResult = [alarmPatternSettingDto1, alarmPatternSettingDto2, alarmPatternSettingDto3];
//            let alarmResolve = _.sortBy(alarmResult, [function(o) { return o.alarmPatternCD; }]);
//            dfd.resolve(alarmResolve);
//            return dfd.promise(alarmResolve);
            return nts.uk.request.ajax("at", paths.getAlarmPattern);
        }        

        export function getCheckConditionCode(): JQueryPromise<Array<share.AlarmCheckConditonCodeDto>> {
            let enum1 ={
                value: 1,
                fieldName: "日次",
                localizedName : ""    
            } 
            let enum2 ={
                value: 2,
                fieldName: "年休付与用出勤率",
                localizedName : ""    
            } 
            let enum3 ={
                value: 3,
                fieldName: "スケジュール日次",
                localizedName : ""    
            }                                
            let alarmCheck1 ={
                category: enum1,
                checkConditonCode: '001',
                checkConditionName: 'checkCondition001',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }
            let alarmCheck2 ={
                category: enum1,
                checkConditonCode: '002',
                checkConditionName: 'checkCondition002',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }
            let alarmCheck3 ={
                category: enum2,
                checkConditonCode: '003',
                checkConditionName: 'checkCondition003',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }                        
            let alarmCheck4 ={
                category: enum2,
                checkConditonCode: '004',
                checkConditionName: 'checkCondition004',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }            
            let alarmCheck5 ={
                category: enum2,
                checkConditonCode: '005',
                checkConditionName: 'checkCondition005',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }            
            let alarmCheck6={
                category: enum3,
                checkConditonCode: '006',
                checkConditionName: 'checkCondition006',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }            
            let alarmCheck7 ={
                category: enum3,
                checkConditonCode: '007',
                checkConditionName: 'checkCondition007',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }            
                                
            let alarmCheck8 ={
                category: enum3,
                checkConditonCode: '008',
                checkConditionName: 'checkCondition008',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }            
            let alarmCheck9 ={
                category: enum3,
                checkConditonCode: '009',
                checkConditionName: 'checkCondition009',
                listRoleId: ['0001', '0002', '0003', '0004', '0005', '0006'],   
            }
//            let result = [alarmCheck1, alarmCheck2, alarmCheck3, alarmCheck4, alarmCheck5, alarmCheck6, alarmCheck7, alarmCheck8, alarmCheck9];
//            let resolve = _.map(result, (x) =>{return new share.ModelCheckConditonCode(x) });                   
//            let dfd = $.Deferred();
//            dfd.resolve(resolve);
//            return dfd.promise(resolve);                                      
              return nts.uk.request.ajax("at", paths.getCheckConditionCode);
        }         
        

        /** add Alarm pattern setting */
        export function addAlarmPattern(alarm: share.AddAlarmPatternSettingCommand): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.addAlarmPattern, alarm);
        }
    
        /** remove Alarm pattern setting */
        export function removeAlarmPattern(query: any): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.removeAlarmPattern, query);
        }
        
        /** Update Alarm pattern setting */
        export function updateAlarmPattern(alarm: share.AddAlarmPatternSettingCommand): JQueryPromise<void> {
            return nts.uk.request.ajax("at", paths.updateAlarmPattern, alarm );
        }
 
    

    
    
}
