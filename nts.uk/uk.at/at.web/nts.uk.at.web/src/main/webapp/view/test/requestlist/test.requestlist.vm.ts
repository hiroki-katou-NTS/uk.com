module nts.uk.at.view.test.requestlist.viewmodel {
	import service = nts.uk.at.view.test.requestlist.service;

        export class ScreenModel {
        	companyId: KnockoutObservable<string> = ko.observable('000000000000-0001');
        	historyId: KnockoutObservable<string> = ko.observable('');
        	workplaceId: KnockoutObservable<string> = ko.observable('');
        	parentWorkplaceId: KnockoutObservable<string> = ko.observable('');
        	baseDate: KnockoutObservable<date> = ko.observable(new Date());
        	startDate: KnockoutObservable<date> = ko.observable(new Date());
        	endDate: KnockoutObservable<date> = ko.observable(new Date());
        	listWorkplaceId: KnockoutObservableArray<String> = ko.observable('');
        	data559: KnockoutObservableArray<WorkplaceInforExport> = ko.observableArray([]);
        	data560: KnockoutObservableArray<WorkplaceInforExport> = ko.observableArray([]);
        	data561: KnockoutObservableArray<WorkplaceInforExport> = ko.observableArray([]);
        	data567: KnockoutObservable<string> = ko.observable('');
        	data569: KnockoutObservable<string> = ko.observable('');
        	data571: KnockoutObservable<string> = ko.observable('');
        	data573: KnockoutObservable<string> = ko.observable('');
        	data647: KnockoutObservableArray<WorkPlaceConfigDto> = ko.observableArray([]);	
        
            constructor() {

            }
            start(): JQueryPromise<void> {
            	var self = this;
            	var dfd = $.Deferred<void>();
            	       	
            	dfd.resolve();
                return dfd.promise();
            }
            run559() {
            	var self = this;
            	nts.uk.ui.block.invisible();
            	service.run559({
            		companyId: self.companyId(),
            		baseDate: self.baseDate()
                }).done((data) => {
                	self.data559(data);
                	nts.uk.ui.block.clear();
                }); 
            }
            run560() {
            	var self = this;
            	nts.uk.ui.block.invisible();
            	service.run560({
            		companyId: self.companyId(),
            		baseDate: self.baseDate(),
            		listWorkplaceId: self.listWorkplaceId().split(",");
                }).done((data) => {
                	self.data560(data);
                	nts.uk.ui.block.clear();
                }); 
            }
            run561() {
            	var self = this;
            	nts.uk.ui.block.invisible();
            	service.run561({
            		companyId: self.companyId(),
            		historyId: self.historyId(),
            		listWorkplaceId: self.listWorkplaceId().split(",");
                }).done((data) => {
                	self.data561(data);
                	nts.uk.ui.block.clear();
                }); 
            }
            run567() {
            	var self = this;
            	nts.uk.ui.block.invisible();
            	service.run567({
            		companyId: self.companyId(),
            		baseDate: self.baseDate(),
            		parentWorkplaceId: self.parentWorkplaceId();
                }).done((data) => {
                	self.data567(data);
                	nts.uk.ui.block.clear();
                }); 
            }
            run569() {
            	var self = this;
            	nts.uk.ui.block.invisible();
            	service.run569({
            		companyId: self.companyId(),
            		baseDate: self.baseDate(),
            		workplaceId: self.workplaceId();
                }).done((data) => {
                	self.data569(data);
                	nts.uk.ui.block.clear();
                }); 
            }
            run571() {
            	var self = this;
            	nts.uk.ui.block.invisible();
            	service.run571({
            		companyId: self.companyId(),
            		baseDate: self.baseDate(),
            		workplaceId: self.workplaceId();
                }).done((data) => {
                	self.data571(data);
                	nts.uk.ui.block.clear();
                }); 
            }
            run573() {
            	var self = this;
            	nts.uk.ui.block.invisible();
            	service.run573({
            		companyId: self.companyId(),
            		baseDate: self.baseDate(),
            		workplaceId: self.workplaceId();
                }).done((data) => {
                	self.data573(data);
                	nts.uk.ui.block.clear();
                }); 
            }
            run647() {
            	var self = this;
            	nts.uk.ui.block.invisible();
            	service.run647({
            		companyId: self.companyId(),
            		start: self.startDate(),
            		end: self.endDate();
                }).done((data) => {
                	self.data647(data);
                	nts.uk.ui.block.clear();
                }); 
            }

}
        export class WorkplaceInforExport {
        	workplaceId: string;
        	hierarchyCode: string;
        	workplaceCode: string;
        	workplaceName: string;
        	workplaceDisplayName: string;
        	workplaceGenericName: string;
        	workplaceExternalCode: string;
	}
}
		export class WorkPlaceConfigDto {
			companyId: string;
			historyId: string;
			startDate: date;
			endDate: date;
}

}