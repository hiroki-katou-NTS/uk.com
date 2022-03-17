module nts.uk.at.view.knr001.b.viewmodel {
  import blockUI = nts.uk.ui.block;

  class BoxModel {
    code: number;
    name: string;
    constructor(code: any, name: any) {
      var vm = this;
      vm.code = code;
      vm.name = name;
    }
  }
  export class ScreenModel extends ko.ViewModel {
    // Start: Init variable
    selectFirstIfNull: KnockoutObservable<boolean> = ko.observable(true);
    usageAtrSwitchList: KnockoutObservableArray<any>;
    usageAtr: KnockoutObservable<number> = ko.observable(0);

    frameSetItemList: KnockoutObservableArray<any>;
    frameSet: KnockoutObservable<number> = ko.observable(0);

    midnightAutoCalcSwitchList: KnockoutObservableArray<any>;
    midnightAutoCalc: KnockoutObservable<number> = ko.observable(0);

    earlyOvertime: KnockoutObservable<number> = ko.observable(0);
    earlyOvertimeMn: KnockoutObservable<number> = ko.observable(0);
    overtime: KnockoutObservable<number> = ko.observable(0);
    overtimeMn: KnockoutObservable<number> = ko.observable(0);

    holidayWorkStatutory: KnockoutObservable<number> = ko.observable(0);
    holidayWorkNotStatutory: KnockoutObservable<number> = ko.observable(0);
    holidayWorkNotStatHoliday: KnockoutObservable<number> = ko.observable(0);

    holidayWorkMnStatutory: KnockoutObservable<number> = ko.observable(0);
    holidayWorkMnNotStatutory: KnockoutObservable<number> = ko.observable(0);
    holidayWorkMnNotStatHoliday: KnockoutObservable<number> = ko.observable(0);

    optionalOvertimeWorkFrameDto: KnockoutObservableArray<BoxModel> = ko.observableArray([]);
    optionalWorkdayoffFrameDto: KnockoutObservableArray<BoxModel> = ko.observableArray([]);
    //End: Init variable

    constructor() {
      super();
      var vm = this;
      vm.setDataInit();
      vm.usageAtr.valueHasMutated();
      vm.midnightAutoCalc.valueHasMutated();
      vm.usageAtr.subscribe(value => {
        vm.usageAtr(value);
      })
    }

    /**
     * Init Screen
     */
    setDataInit() {
      var vm = this;
      // Data
      vm.usageAtrSwitchList = ko.observableArray([
        { code: 1, name: vm.$i18n("KNR001_63") }, // Use
        { code: 0, name: vm.$i18n("KNR001_64") }, // Do not use
      ]);
      vm.frameSetItemList = ko.observableArray([
        new BoxModel(1, vm.$i18n("KNR001_67")), // Specify overtime / holiday slots
        new BoxModel(0, vm.$i18n("KNR001_68")), // Follow the working hours frame setting
      ]);
      vm.midnightAutoCalcSwitchList = ko.observableArray([
        { code: 1, name: vm.$i18n("KNR001_71") }, // Use
        { code: 0, name: vm.$i18n("KNR001_72") }, // Do not use
      ]);
    }

    /**
     * Process get Details
     */
    getDetails() {
      var vm = this;
      vm.optionalOvertimeWorkFrameDto([new BoxModel(0, vm.$i18n("KNR001_96"))]);
      vm.optionalWorkdayoffFrameDto([new BoxModel(0, vm.$i18n("KNR001_96"))]);
      service.getDetails().done((data) => {
        if (data.optionalOvertimeWorkFrameDto != null) {
          data.optionalOvertimeWorkFrameDto.forEach((e: any) => {
            vm.optionalOvertimeWorkFrameDto.push(
              new BoxModel(e.overtimeWorkFrNo, e.overtimeWorkFrName))
          })
        }
        if (data.optionalWorkdayoffFrameDto != null) {
          data.optionalWorkdayoffFrameDto.forEach((e: any) => {
            vm.optionalWorkdayoffFrameDto.push(
              new BoxModel(e.workdayoffFrNo, e.workdayoffFrName))
          })
        }
        if (data.optionalDeclareSetDto == null) {
          vm.$dialog.info({ messageId: "Msg_3315" });
          return;
        }

        vm.usageAtr(data.optionalDeclareSetDto != null ? data.optionalDeclareSetDto.usageAtr : 0);
        vm.frameSet(data.optionalDeclareSetDto?.frameSet);
        vm.midnightAutoCalc(data.optionalDeclareSetDto != null ? data.optionalDeclareSetDto.midnightAutoCalc : 0);

        vm.earlyOvertime(data.optionalDeclareSetDto?.overtimeFrame?.earlyOvertime);
        vm.earlyOvertimeMn(data.optionalDeclareSetDto?.overtimeFrame?.earlyOvertimeMn);
        vm.overtime(data.optionalDeclareSetDto?.overtimeFrame?.overtime);
        vm.overtimeMn(data.optionalDeclareSetDto?.overtimeFrame?.overtimeMn);

        vm.holidayWorkStatutory(data.optionalDeclareSetDto?.holidayWorkFrame?.holidayWork?.statutory);
        vm.holidayWorkNotStatutory(data.optionalDeclareSetDto?.holidayWorkFrame?.holidayWork?.notStatutory);
        vm.holidayWorkNotStatHoliday(data.optionalDeclareSetDto?.holidayWorkFrame?.holidayWork?.notStatHoliday);

        vm.holidayWorkMnStatutory(data.optionalDeclareSetDto?.holidayWorkFrame?.holidayWorkMn?.statutory);
        vm.holidayWorkMnNotStatutory(data.optionalDeclareSetDto?.holidayWorkFrame?.holidayWorkMn?.notStatutory);
        vm.holidayWorkMnNotStatHoliday(data.optionalDeclareSetDto?.holidayWorkFrame?.holidayWorkMn?.notStatHoliday);
      }).always(() => {
        $('#B1_2').focus();
      });;
    }

    isEnable() {
      var vm = this;
      return vm.usageAtr() === 1 && vm.frameSet() === 1;
    }

    isEnableWithUsageAtr() {
      var vm = this;
      return vm.usageAtr() === 1;
    }

    /**
     * startPage
     */
    public startPage(): JQueryPromise<void> {
      let vm = this;
      var dfd = $.Deferred<void>();
      blockUI.invisible();
      blockUI.clear();
      vm.getDetails();
      dfd.resolve();
      return dfd.promise();
    }

    /**
     * Register DeclareSet
     */
    proceed() {
      let vm = this;
      // Create Json Body
      const command = {
        usageAtr: vm.usageAtr(),
        frameSet: vm.frameSet(),
        midnightAutoCalc: vm.midnightAutoCalc(),
        overtimeFrame: {
          earlyOvertime: vm.earlyOvertime(),
          earlyOvertimeMn: vm.earlyOvertimeMn(),
          overtime: vm.overtime(),
          overtimeMn: vm.overtimeMn()
        },
        holidayWorkFrame: {
          holidayWork: {
            statutory: vm.holidayWorkStatutory(),
            notStatutory: vm.holidayWorkNotStatutory(),
            notStatHoliday: vm.holidayWorkNotStatHoliday()
          },
          holidayWorkMn: {
            statutory: vm.holidayWorkMnStatutory(),
            notStatutory: vm.holidayWorkMnNotStatutory(),
            notStatHoliday: vm.holidayWorkMnNotStatHoliday()
          }
        }
      }
      // Call API Register
      service.register(command).done(() => {
        nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function () {
          //Reload
          vm.getDetails();
        });
      }).fail(error => {
        vm.$dialog.error({ messageId: error.messageId });
      }).always(() => {
        $("#B2_1").focus();
        blockUI.clear();
      });
    }

    /**
     * Function: Close Modal
     */
    cancel() {
      nts.uk.ui.windows.close();
    }
  }
}
