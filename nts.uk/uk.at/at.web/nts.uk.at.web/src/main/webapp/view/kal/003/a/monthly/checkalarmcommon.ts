module nts.uk.at.view.kal003.a.tab {
    import model = kal003.share.model;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    
    export class CheckAlarmTab {
        
        
        constructor() {
            let self = this;
            
            $("#fixed-table2").ntsFixedTable({ height: 200 });
        }
    }
}