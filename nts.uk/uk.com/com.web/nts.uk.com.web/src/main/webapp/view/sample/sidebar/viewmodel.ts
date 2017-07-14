module sample.sidebar.viewmodel {
	export class ScreenModel {
	        show: KnockoutObservable<boolean>;
	        enable: KnockoutObservable<boolean>;
	
	        items: KnockoutObservableArray<ItemModel>;
	        columns: KnockoutObservableArray<NtsGridListColumn>;
	        currentCode: KnockoutObservable<any>;
	        currentCodeList: KnockoutObservableArray<any>;
	
	        constructor() {
	        	var self = this;
	            self.show = ko.observable(true);
	            self.show.subscribe(function(newVal) {
	                if (newVal)
	                    $("#sidebar").ntsSideBar("show", 1);
	                else
	                    $("#sidebar").ntsSideBar("hide", 1);
	            });
	
	            this.items = ko.observableArray([]);
	            
	            for(let i = 1; i < 5; i++) {
	                this.items.push(new ItemModel('00' + i, '基本給', "description " + i, i%3 === 0, "2010/1/1"));
	            }
	            
	            this.columns = ko.observableArray([
	                { headerText: 'コード', key: 'code', width: 100, hidden: true },
	                { headerText: '名称', key: 'name', width: 150, hidden: true }, 
	                { headerText: '説明', key: 'description', width: 150 }, 
	                { headerText: '説明1', key: 'other1', width: 150},
	                { headerText: '説明2', key: 'other2', width: 150, isDateColumn: true, format: 'YYYY/MM/DD' } 
	            ]); 
	            self.currentCode = ko.observable("001");
	            self.currentCodeList = ko.observableArray([]);
	            
	            self.enable = ko.observable(true);
	            self.enable.subscribe(function(newVal) {
	                if (newVal) {
	                    $("#sidebar").ntsSideBar("enable", 1);
	                    $("#sidebar").ntsSideBar("enable", 2);
	                }
	                else {
	                    $("#sidebar").ntsSideBar("disable", 1);
	                    $("#sidebar").ntsSideBar("disable", 2);
	                }
	            });
	            
	            $("#sidebar").ntsSideBar("init", {
	            	activate: (event, info) => {
	            		console.log(info);
	            	}
	            });
	            
	            $("#grid").igGrid({
	                dataSource: self.items(),
	                primaryKey: "code",
	                width: '100%',
	                height: '350px',
	                columns: [
	                    { headerText: 'コード', key: 'code', width: '30%' },
	                    { headerText: '名称', key: 'name', width: '30%' }, 
	                    { headerText: '説明', key: 'description', width: '30%' }, 
	                ],
	                virtualization: true,
	                virtualizationMode: 'continuous',
	            });
	        }
	
	        testSideMenu() {
	        	alert($("#sidebar").ntsSideBar("getCurrent"));
	        }
	
	        openSubWindow() {
	            nts.uk.ui.windows.sub.modeless("/view/sample/sidebar/sidebar-sub.xhtml");
	        }
	        
	        openNewTab() {
	            window.open("/nts.uk.com.web/view/sample/sidebar/sidebar-sub.xhtml", "_blank").focus();
	        }
	
	    }
	
    class ItemModel {
        code: string;
        name: string;
        description: string;
        other1: string;
        other2: string;
        deletable: boolean;
        constructor(code: string, name: string, description: string, deletable: boolean, other1?: string, other2?: string) {
            this.code = code;
            this.name = name;
            this.description = description;
            this.other1 = other1;
            this.other2 = other2 || other1;    
            this.deletable = deletable;    
        }
    }
}