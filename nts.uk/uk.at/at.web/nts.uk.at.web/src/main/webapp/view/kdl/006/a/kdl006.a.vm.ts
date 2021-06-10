module nts.uk.at.view.kdl006.a {
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import info = nts.uk.ui.dialog.info;
    import error = nts.uk.ui.dialog.error;
    import windows = nts.uk.ui.windows;
    
    export class ScreenModel {
        tighteningList = ko.observableArray([]);
        selectedId = ko.observable('');
        workplaceList = ko.observableArray([]);
        width = ko.observable('614');
        descriptive = ko.observable('');
        
        workPlaceComfirmList = [];
        
        constructor(){
            let self = this;
            self.selectedId.subscribe((newValue) => {
                let closure = _.find(self.tighteningList(), ['closureId', newValue]);
                self.descriptive(getText('KDL006_15',[closure ? closure.closureName:'']));
                self.getWorkplace();
            });
            self.workplaceList.subscribe(() => {
                if(self.workplaceList().length > 12){
                    $('.scroll').css({"width": "647", "overflow-y": "scroll"});
                }else{
                    $('.scroll').css({"width": "630", "overflow-y": "hidden"});
                }
            });
        }
        
        startPage(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            block.grayout();
            $.when(self.getSelectedClosure(), service.startPage()).done(function(closureSelected, data) {
                let c = [];
                _.forEach(data, function(closure) {
                    c.push(new Closure(closure));
                });
                self.tighteningList(c);
                self.selectedId(closureSelected);
                dfd.resolve();
            }).fail(function(res) {
                error({ messageId: res.messageId });
            }).always(() =>{
            });
            
            return dfd.promise();
        }

		getSelectedClosure(): JQueryPromise<number> {
			let dfd = $.Deferred<number>();
			const key = __viewContext.user.employeeId + '' + __viewContext.user.companyId;
			nts.uk.characteristics.restore(key).done(function(data) {
				if (!nts.uk.util.isNullOrEmpty(data) && !nts.uk.util.isNullOrEmpty(data.employmentInfo) && !nts.uk.util.isNullOrEmpty(data.employmentInfo.selectedClosureId)) {
					dfd.resolve(data.employmentInfo.selectedClosureId);
				} else {
					nts.uk.request.ajax('com', 'bs/employee/employment/history/getcurrenthistoryitem').done(item => {
						if (item) {
							nts.uk.request.ajax("at","ctx/at/shared/workrule/closure/getclosuretiedbyemployment/"+item.employmentCode).done(id => 
								dfd.resolve(id)
							);
						} else {
							const DEFAULT_VALUE = 1;
							// Q&A: #88282 (update specs)
							dfd.resolve(DEFAULT_VALUE);
						}
					});
				}
			});
			return dfd.promise();
		}
        
        getWorkplace(): void {
            let self = this;
            block.grayout();
            let closure = _.find(self.tighteningList(), ['closureId', self.selectedId()]);
            if(closure){
                service.getWorkplace(closure).done(function(data) {
                    self.workPlaceComfirmList = data;
                    if(data.length == 0){
                        error({ messageId: 'Msg_1653' });    
                    }
                    let w = [];
                    for(let i = 0; i < (data.length < 12 ? 12 : data.length); i ++){
                        w.push(new WorkPlace(data[i]));
                    }
                    self.workplaceList(w);
                }).fail(function(res) {
                    error({ messageId: res.messageId });
                }).always(() =>{
                    block.clear();
                    $('#combo-box').focus();
                });
            }
        }
        
        save(){
            let self = this;
            let workPlaces: any = [];
            _.forEach(self.workplaceList(), function(row) {
                let w = _.find(self.workPlaceComfirmList, ['workPlaceId', row.workPlaceId]);
                if(w && row.confirmEmployment() != w.confirmEmployment){
                    row.closureId = self.selectedId();
                    row.currentMonth = _.find(self.tighteningList(), ['closureId', self.selectedId()]).yearMonth;
                    row.confirmEmployment(row.confirmEmployment()?false:true);
					workPlaces.push(row);
                }    
            });
            if(workPlaces.length > 0){
                block.grayout();
                service.save(ko.toJS(workPlaces)).done(function(data) {
                    self.getWorkplace();
                    info({messageId: 'Msg_15'});
                }).fail(function(res) {
                    error({ messageId: res.messageId });
                }).always(() =>{
                });
            }
        }
        
        close(): any {
            windows.close();
        }
    }
    
    class WorkPlace {
        workPlaceId = '';
        workPlaceName = '';
        confirmEmployment = ko.observable(false);
        confirmEmployeeName = '';
        confirmationTime = '';       
        constructor(param: any) {
            let self = this;
            if(param){
                self.workPlaceId = param.workPlaceId;
                self.workPlaceName = param.workPlaceName;
                self.confirmEmployment(param.confirmEmployment);
                self.confirmEmployeeName = param.confirmEmployeeName;
                self.confirmationTime = param.confirmationTime;
            }
        }
    }
    
    class Closure {
        closureId: string
        yearMonth: number;
        closureName: string;
        start: Date;
        end: Date;
        periodDate: string;
        constructor(param: any) {
            let self = this;
            self.closureId = param.closureId;
            self.yearMonth = param.yearMonth;
            self.closureName = param.closureName;
            self.start = new Date(param.start);
            self.end = new Date(param.end);
            self.periodDate = param.start + getText('KDL006_20') + param.end;
        }
    }
}
