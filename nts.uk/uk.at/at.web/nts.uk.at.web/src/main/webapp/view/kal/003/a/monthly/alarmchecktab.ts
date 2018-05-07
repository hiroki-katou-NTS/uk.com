module nts.uk.at.view.kal003.a.tab {
    import windows = nts.uk.ui.windows;
    import getText = nts.uk.resource.getText;
    import block = nts.uk.ui.block;
    import model = nts.uk.at.view.kal003.share.model;
    import shareutils = nts.uk.at.view.kal003.share.kal003utils;

    export class AlarmcheckTab {
        

        constructor() {
            let self = this;

            $("#fixed-table1").ntsFixedTable({ height: 200 });
        }//end constructor
    }//end FixedCheckConditionTab
}//end tab