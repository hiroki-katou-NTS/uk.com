/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg005.a.screenModel {
  import object = nts.uk.at.view.ccg005.a.object;
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  const API = {
    getDisplayAttendanceData: 'screen/com/ccg005/get-display-attendance-data',
    getDisplayInfoAfterSelect: 'screen/com/ccg005/get-information-after-select',
    getAttendanceInformation: 'screen/com/ccg005/get-attendance-information',
    saveFavorite: 'ctx/office/favorite/save',
    registerComment: 'ctx/office/comment/register',
    deleteComment: 'ctx/office/comment/delete'
  };
  const ID_AVATAR_CHANGE = 'ccg005-avatar-change';

  @component({
    name: 'ccg005-component',
    template: `<div style="display: flex; position: relative; overflow-x: hidden; overflow-y: auto; height: 440px" id="ccg005-watching">
    <div id="ccg005-content">
      <div style="margin: 0 10px;">
        <div class="grade-header-top">
          <!-- A0 -->
          <span data-bind="i18n: 'CCG005_1'" class="ccg005-bold"></span>
          <!-- A1_5 -->
          <i data-bind="visible: $component.inCharge, ntsIcon: {no: 5, width: 25, height: 25}, click: $component.openScreenCCG005B"></i>
          &#160;
          <!-- A1_6 -->
          <i data-bind="ntsIcon: {no: 194, width: 25, height: 25}"></i>
        </div>
        <div class="grade-header-center" style="padding-bottom: 5px;">
          <table>
            <tr>
              <td>
                <!-- A1_1 -->
                <div id="ccg005-avatar-change"></div>
              </td>
              <td style="padding-left: 5px; width: 370px;">
                <!-- A1_2 -->
                <span class="ccg005-bold" data-bind="text: $component.businessName()"></span>
                <div class="ccg005-flex none-enter-icon">
                  <!-- A1_3 -->
                  <i data-bind="ntsIcon: {no: $component.emoji(), width: 20, height: 30}"></i>
                  <div style="position: relative;" class="CCG005-A1_4-border">
                    <!-- A1_4 -->
                    <input id="CCG005-A1_4" style="border: none !important; padding-right: 30px; background: none !important;" data-bind="ntsTextEditor: {
                      enterkey: $component.registerComment,
                      value: $component.comment,
                      enable: $component.isBaseDate,
                      option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                        textmode: 'text',
                        width: '250px',
                        placeholder: $component.$i18n('CCG005_35')
                      }))
                    }, visible: $component.isSameOrBeforeBaseDate"/>
                    <i class="ccg005-clearbtn" style="position: absolute; right: 5px; visibility: hidden;" data-bind="click: $component.deleteComment, ntsIcon: {no: $component.emoji(), width: 20, height: 28}"></i>
                  </div>
                </div>
              </td>
              <td>
                <!-- A1_7 -->
                <i class="ccg005-status-img-A1_7" data-bind="ntsIcon: { no: $component.activityStatusIcon(), width: 20, height: 20 }, visible: $component.isBaseDate"></i>
                <i data-bind="ntsIcon: { no: 191, width: 20, height: 20 }, visible: $component.isDiffBaseDate"></i>
              </td>
            </tr>
          </table>
        </div>
        <div class="grade-header-bottom ccg005-flex" style="position: relative;">
          <!-- A2_3 -->
          <div data-bind="ntsDatePicker: {
            name: '#[CCG005_36]',
            value: selectedDate,
            dateFormat: 'YYYY/MM/DD',
            fiscalMonthsMode: true,
            showJumpButtons: true
          }"></div>
          <!-- A2_1 -->
          <button id="ccg005-legends" style="margin-left: 5px;" data-bind="visible: $component.isBaseDate, ntsLegendButton: legendOptions"></button>
          <div style="right: 0; position: absolute; display: flex; align-items: center;">
            <!-- A3_2 -->
            <i id="ccg005-star-img" style="margin-right: 5px;" data-bind="ntsIcon: {no: 184, width: 20, height: 20}"></i>
            <!-- A3_1 -->
            <div data-bind="ntsComboBox: {
              width: '120px',
              options: favoriteSpecifyData,
              editable: true,
              visibleItemsCount: 5,
              value: favoriteInputDate,
              optionsValue: 'inputDate',
              optionsText: 'favoriteName',
              required: true,
              columns: [
                { prop: 'favoriteName' }
              ]}"></div>
          </div>
        </div>
        <div class="grade-body-top" style="padding-top: 5px;">
          <table style="width: 100%;">
            <tr>
              <td style="width: 35px;"></td>
              <!-- A2_5 -->
              <td class="ccg005-w100 ccg005-pl-5">
                <span class="ccg005-bold" data-bind="i18n: 'CCG005_41'"></span>
              </td>
              <!-- A2_6 -->
              <td class="ccg005-w100 ccg005-pl-5">
                <span class="ccg005-bold" data-bind="i18n: 'CCG005_42'"></span>
              </td>
              <td></td>
              <td></td>
            </tr>
          </table>
        </div>
        <!-- A5 -->
        <div class="grade-body-bottom" style="min-height: 55px; height: 255px;">
          <table style="width: 100%; border-collapse: separate; border-spacing: 0 5px">
          <tbody data-bind="foreach: attendanceInformationDtosDisplay">




            <tr style="background-color: yellow; height: 45px;">
              <td style="padding-right: 5px; width: 30px; background-color: white;">
                <!-- A4_1 -->
                <img style="border-radius: 50%; border: 1px groove;" width="30px" height="30px" src="${__viewContext.rootPath}/view/ccg/005/a/header.png"/>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset">
                <!-- A4_8 -->
                <label class="ccg005-w100" style="display: inline-block;" data-bind="text: businessName"/>
                <!-- A4_5 -->
                <div>
                  <i data-bind="ntsIcon: {no: $component.emoji(), width: 20, height: 15}"></i>
                </div>
              </td>
              <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <div class="ccg005-w100">
                  <!-- A4_2 -->
                  <label data-bind="text: attendanceDetailDto.workName"/>
                  <!-- A4_4 -->
                  <i data-bind="ntsIcon: {no: 190, width: 13, height: 13}"></i>
                </div>
                <div>
                <!-- A4_3 -->
                  <label data-bind="text: attendanceDetailDto.checkInCheckOutTime"/>
                </div>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
                <!-- A4_7 -->
                <span class="ccg005-flex">
                  <i class="ccg005-status-img" data-bind="ntsIcon: {no: 191, width: 20, height: 20}"></i>
                </span>
              </td>
              <td class="ccg005-pl-5 ccg005-border-groove ccg005-left-unset">
                <!-- A4_6 time -->
                <p style="max-width: 125px;" data-bind="text: goOutDto.goOutPeriod"/>
                <!-- A4_6 text -->
                <p style="max-width: 125px;" class="limited-label" data-bind="text: goOutDto.goOutReason"/>
              </td>
            </tr>



            </tbody>
          </table>
        </div>
        <div class="grade-bottom ccg005-flex" style="width: 100%; align-items: center; position: relative; margin-top: 5px; margin-bottom: 10px;">
          <table style="width: 100%;">
            <tr>
              <td>
                <div class="ccg005-pagination ccg005-flex">
                  <!-- A5_1 -->
                  <i class="ccg005-pagination-btn" data-bind="ntsIcon: {no: 193, width: 15, height: 20}, click: $component.previousPage"></i>
                  <!-- A5_2 -->
                  <span style="white-space: nowrap;" data-bind="text: $component.paginationText()"></span>
                  <!-- A5_3 -->
                  <i class="ccg005-pagination-btn" data-bind="ntsIcon: {no: 192, width: 15, height: 20}, click: $component.nextPage"></i>
                </div>
              </td>
              <td style="width: 100%">
                <div class="ccg005-switch-btn" style="float: right;">
                  <!-- A5_4 -->
                  <div class="cf ccg005-switch" data-bind="ntsSwitchButton: {
                    options: contentSelections,
                    optionsValue: 'code',
                    optionsText: 'name',
                    value: contentSelected }"></div>
                </div>
              </td>
            </tr>
          </table>
        </div>
      </div>

      <!-- A3_2 Popup -->
      <div id="ccg005-star-popup" style="width: 212px;">
        <!-- A3_2.1 -->
        <table>
          <tr>
            <td style="text-align: right;">
              <a style="color: blue; text-decoration: underline;" data-bind="i18n: 'CCG005_34', click: $component.openScreenCCG005D"></a>
            </td>
          </tr>
          <tr>
            <td style="padding-top: 10px;">
              <!-- A3_2.2 -->
              <input data-bind="ntsTextEditor: {
                value: searchValue,
                enterkey: $component.registerComment,
                option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                  textmode: 'text',
                  width: '190px',
                  placeholder: $component.$i18n('CCG005_45')
                }))
              }"></input>
            </td>
          </tr>
          <tr>
            <td style="padding-top: 10px;">
              <!-- A3_2.3 -->
              <button data-bind="i18n: 'CCG005_32', click: $component.openCDL008"></button>
              <!-- A3_2.4 -->
              <label class="limited-label" style="max-width: 125px;" data-bind="text: $component.workplaceNameFromCDL008"></label>
            </td>
          </tr>
        </table>
      </div>
      <!-- A1_7 & A4_7 Popup -->
      <div id="ccg005-status-popup">
        <table>
          <tr data-bind="click: $component.registerAttendanceStatus">
            <td>
              <i data-bind="visible: $component.visibleNotPresent(), ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.1 -->
            <td>
              <i data-bind="ntsIcon: {no: 196, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_43'"></td>
          </tr>
          <tr>
            <td>
              <i data-bind="visible: $component.visiblePresent(), ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.2 -->
            <td>
            <i data-bind="ntsIcon: {no: 195, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_22'"></td>
          </tr>
          <tr data-bind="click: $component.openScreenCCG005E">
            <td>
              <i data-bind="visible: $component.visibleGoOut(), ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.3 -->
            <td>
            <i data-bind="ntsIcon: {no: 191, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_39'"></td>
          </tr>
          <tr>
            <td>
              <i data-bind="visible: $component.visibleGoHome(), ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.4 -->
            <td>
            <i data-bind="ntsIcon: {no: 196, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_44'"></td>
          </tr>
          <tr>
            <td>
              <i data-bind="visible: $component.visibleHoliday(), ntsIcon: {no: 78, width: 15, height: 25}"></i>
            </td>
            <!-- A1_7.5 -->
            <td>
            <i data-bind="ntsIcon: {no: 197, width: 20, height: 20}"></i>
            </td>
            <td data-bind="i18n: 'CCG005_40'"></td>
          </tr>
        </table>
      </div>
    </div>
  </div>
  <style>
    .ccg005-w100 {
      width: 100px;
    }
    .ccg005-bold {
      font-weight: bold;
    }
    .ccg005-pl-5 {
      padding-left: 5px;
    }
    .ccg005-border-groove {
      border: 1px groove;
    }
    .ccg005-right-unset {
      border-right: none;
    }
    .ccg005-left-unset {
      border-left: none;
    }
    .ccg005-flex {
      display: flex;
    }
    .grade-header-top {
      display: flex;
      margin-top: 5px;
    }
    .grade-header-top span {
      width: 390px;
    }
    .bg-white-ccg005 {
      background-color: #FFFFFF;
    }
    .bg-green-ccg005 {
      background-color: #99FF99
    }
    .bg-yellow-ccg005 {
      background-color: #FFFF00
    }
    .bg-gray-ccg005 {
      background-color: #D9D9D9
    }
    #ccg005-content {
      width: calc(100% - 5px);
      border: 1px groove black;
    }
    #ccg005-status-popup table tr,
    #ccg005-content i, #ccg005-content img {
      cursor: pointer;
    }
    #ccg005-status-popup table tr td {
      padding-right: 5px;
    }
    #CCG005_A1_1 {
      max-height: 40px;
      max-width: 40px;
      min-height: 40px;
      min-width: 40px;
      border-radius: 50%;
    }
    #CCG005_no_avatar {
      display: flex;
      align-items: center;
      background-color: #eeeeee;
      color: blue;
      border: 1px solid #333688;
      border-radius: 50%;
      width: 40px;
      height: 40px;
    }
    .ccg005-switch > .nts-switch-button {
      width: 80px;
    }
  </style>`
  })
  export class ViewModel extends ko.ViewModel {
    selectedDate: KnockoutObservable<string> = ko.observable('');
    legendOptions: any = {
      items: [
        { cssClass: { className: 'bg-white-ccg005', colorPropertyName: 'background-color' }, labelText: this.$i18n('CCG005_26') },  // A2_1.1
        { cssClass: { className: 'bg-green-ccg005', colorPropertyName: 'background-color' }, labelText: this.$i18n('CCG005_22') },  // A2_1.2
        { cssClass: { className: 'bg-yellow-ccg005', colorPropertyName: 'background-color' }, labelText: this.$i18n('CCG005_39') }, // A2_1.3
        { cssClass: { className: 'bg-gray-ccg005', colorPropertyName: 'background-color' }, labelText: this.$i18n('CCG005_40') }    // A2_1.4
      ]
    };
    contentSelections: KnockoutObservableArray<any> = ko.observableArray([
      { code: '1', name: this.$i18n('CCG005_37') },
      { code: '2', name: this.$i18n('CCG005_38') }
    ]);
    contentSelected: KnockoutObservable<any> = ko.observable(1);
    favoriteInputDate: KnockoutObservable<any> = ko.observable('');
    searchValue: KnockoutObservable<string> = ko.observable('');
    workplaceNameFromCDL008: KnockoutObservable<string> = ko.observable('');

    // Pagination
    currentPage: KnockoutObservable<number> = ko.observable(1);
    perPage: KnockoutObservable<number> = ko.observable(5);
    totalElement: KnockoutObservable<number> = ko.observable(7);
    totalRow: KnockoutComputed<number> = ko.computed(() =>
      this.perPage() * this.currentPage() > this.totalElement()
        ? this.totalElement() - this.perPage() * (this.currentPage() - 1)
        : this.perPage());
    startPage: KnockoutComputed<number> = ko.computed(() => this.perPage() * (this.currentPage() - 1) + 1);
    endPage: KnockoutComputed<number> = ko.computed(() => this.startPage() + this.totalRow() - 1);
    paginationText: KnockoutComputed<string> = ko.computed(() => `${this.startPage()}-${this.endPage()}/${this.totalElement()}`);
    // End pagination

    activityStatus: KnockoutObservable<number> = ko.observable(0);
    activatedStatus: KnockoutObservable<number> = ko.observable(0);
    activityStatusIcon: KnockoutComputed<number> = ko.computed(() => this.initActivityStatus(this.activityStatus()));
    businessName: KnockoutObservable<string> = ko.observable('');
    emoji: KnockoutObservable<number> = ko.observable(187);
    comment: KnockoutObservable<string> = ko.observable('');
    commentDate: KnockoutObservable<any> = ko.observable('');
    avatarPath: KnockoutObservable<string> = ko.observable('');
    visibleNotPresent: KnockoutComputed<boolean> = ko.computed(() => this.activatedStatus() === StatusClassfication.NOT_PRESENT);
    visiblePresent: KnockoutComputed<boolean> = ko.computed(() => this.activatedStatus() === StatusClassfication.PRESENT);
    visibleGoOut: KnockoutComputed<boolean> = ko.computed(() => this.activatedStatus() === StatusClassfication.GO_OUT);
    visibleGoHome: KnockoutComputed<boolean> = ko.computed(() => this.activatedStatus() === StatusClassfication.GO_HOME);
    visibleHoliday: KnockoutComputed<boolean> = ko.computed(() => this.activatedStatus() === StatusClassfication.HOLIDAY);
    favoriteSpecifyData: KnockoutObservableArray<any> = ko.observableArray([]);
    emojiUsage: KnockoutObservable<boolean> = ko.observable(false);
    isBaseDate: KnockoutObservable<boolean> = ko.observable(true);
    isSameOrBeforeBaseDate: KnockoutObservable<boolean> = ko.observable(true);
    inCharge: KnockoutObservable<boolean> = ko.observable(true);
    workplaceFromCDL008: KnockoutObservableArray<string> = ko.observableArray([]);
    employeeIdList: KnockoutObservableArray<string> = ko.observableArray([]);
    personalIdList: KnockoutObservableArray<string> = ko.observableArray([]);
    attendanceInformationDtos: KnockoutObservableArray<object.AttendanceInformationDto> = ko.observableArray([]);
    attendanceInformationDtosDisplay: KnockoutObservableArray<AttendanceInformationViewModel> = ko.observableArray([]);
    listPersonalInfo: KnockoutObservableArray<any> = ko.observableArray([]);

    //data for screen E
    goOutParams: KnockoutObservable<GoOutParam> = ko.observable();
    isDiffBaseDate: KnockoutComputed<boolean> = ko.computed(() => !this.isBaseDate());

    created() {
      const vm = this;
      vm.selectedDate(moment.utc().format('YYYYMMDD'));
      vm.toStartScreen();
    }

    mounted() {
      const vm = this;
      $('#ccg005-legends').click(() => {
        $('.nts-legendbutton-panel').css('padding', '5px 10px');
        $('.legend-item-symbol').first().css('border', '1px groove');
        $('.legend-item').css('margin-bottom', '5px');
      });
      vm.initResizeable(vm);
      vm.initPopupArea();
      vm.initPopupStatus();
      vm.initChangeFavorite();
      vm.initFocusA1_4();
      vm.initChangeSelectedDate();
      vm.perPage.subscribe(() => vm.currentPage(1));
    }

    /**
     * 日付を更新する時
     */
    initChangeSelectedDate() {
      const vm = this;
      vm.selectedDate.subscribe(() => {
        const selectedDate = moment.utc(moment.utc(vm.selectedDate()).format('YYYY/MM/DD'));
        const baseDate = moment.utc(moment.utc().format('YYYY/MM/DD'));
        vm.isSameOrBeforeBaseDate(selectedDate.isSameOrBefore(baseDate));
        vm.isBaseDate(selectedDate.isSame(baseDate));
        // パラメータ「在席情報を取得」
        const empIds = _.map(vm.attendanceInformationDtos(), atd => {
          return {
            sid: atd.sid,
            pid: _.find(vm.listPersonalInfo(), item => item.employeeId === atd.sid).personalId
          };
        });
        const param = {
          empIds: empIds,
          baseDate: vm.selectedDate(),
          emojiUsage: vm.emojiUsage()
        }
        vm.$blockui('show');
        vm.$ajax('com', API.getAttendanceInformation, param).then((res: object.AttendanceInformationDto) => {

        }).always(() => vm.$blockui('hide'));
      });
    }

    /**
     * お気に入りを選択する時
     */
    private initChangeFavorite() {
      const vm = this;
      vm.favoriteInputDate.subscribe(() => {
        const selectedFavorite = _.find(vm.favoriteSpecifyData(), item => item.inputDate === vm.favoriteInputDate());
        const param: DisplayInfoAfterSelectParam = new DisplayInfoAfterSelectParam({
          baseDate: vm.selectedDate(),
          emojiUsage: vm.emojiUsage(),
          wkspIds: selectedFavorite ? selectedFavorite.workplaceId : []
        });
        vm.$blockui('show');
        vm.$ajax('com', API.getDisplayInfoAfterSelect, param).then((res: object.DisplayInformationDto) => {
          vm.attendanceInformationDtos(res.attendanceInformationDtos);
          vm.listPersonalInfo(res.listPersonalInfo);
          const display = vm.getAttendanceInformationDtosDisplay(res);
          vm.attendanceInformationDtosDisplay(display);
        })
          .always(() => vm.$blockui('clear'));
      });
    }

    private getAttendanceInformationDtosDisplay(res: object.DisplayInformationDto): AttendanceInformationViewModel[] {
      const vm = this;
      return _.map(res.attendanceInformationDtos, (item => {
        let businessName = "";
        const personalInfo = _.find(res.listPersonalInfo, (emp => emp.employeeId === item.sid));
        if (personalInfo) {
          businessName = personalInfo.businessName;
        }
        console.log(vm.getGoOutViewModel(item.goOutDto));
        return new AttendanceInformationViewModel({
          applicationDtos: item.applicationDtos,
          sid: item.sid,
          attendanceDetailDto: vm.getAttendanceDetailViewModel(item.attendanceDetailDto),
          avatarDto: item.avatarDto,
          activityStatusDto: item.activityStatusDto,
          commentDto: item.commentDto,
          goOutDto: vm.getGoOutViewModel(item.goOutDto),
          emojiDto: item.emojiDto,
          businessName: businessName
        });
      }));
    }

    private getAttendanceDetailViewModel(attendanceDetailDto: any): AttendanceDetailViewModel {
      let checkInCheckOutTime = "";
      if (attendanceDetailDto.checkInTime === null && attendanceDetailDto.checkOutTime === null) {
        checkInCheckOutTime = (attendanceDetailDto.checkInTime + " - " + attendanceDetailDto.checkOutTime);
      }
      if (!attendanceDetailDto.checkInTime === null && attendanceDetailDto.checkOutTime === null) {
        checkInCheckOutTime = attendanceDetailDto.checkOutTime;
      }
      if (attendanceDetailDto.checkInTime === null && !attendanceDetailDto.checkOutTime === null) {
        checkInCheckOutTime = attendanceDetailDto.checkInTime;
      }
      return new AttendanceDetailViewModel({
        workColor: attendanceDetailDto.workColor,
        workName: attendanceDetailDto.workName,
        checkOutColor: attendanceDetailDto.checkOutColor,
        checkInCheckOutTime: checkInCheckOutTime,
        checkInColor: attendanceDetailDto.checkInColor,
        workDivision: attendanceDetailDto.workDivision,
      });
    }

    private getGoOutViewModel(goOutDto: object.GoOutEmployeeInformationDto): GoOutEmployeeInformationViewModel {
      let period = "";
      if (goOutDto.goOutTime && goOutDto.comebackTime) {
        period = String(goOutDto.goOutTime + " - " + goOutDto.comebackTime);
      }
      if (goOutDto.goOutTime && !goOutDto.comebackTime) {
        period = String(goOutDto.goOutTime);
      } 
      if (!goOutDto.goOutTime && goOutDto.comebackTime) {
        period = String(goOutDto.comebackTime);
      } 
      return new GoOutEmployeeInformationViewModel({
        goOutReason: goOutDto.goOutReason,
        goOutPeriod: period
      });
    }

    private initResizeable(vm: any) {
      $(window).on('ccg005.resize', () => {
        const subHeight = $('#ccg005-content').height()
          - $('.grade-header-top').height()
          - $('.grade-header-center').height()
          - $('.grade-header-bottom').height()
          - $('.grade-body-top').height()
          - $('.grade-bottom').height()
          - 40;
        if (subHeight >= 48) {
          vm.perPage(_.floor(subHeight/48));
        }
        $('.grade-body-bottom').height(subHeight);
      });
    }

    /**
     * Popup A3_2
     */
    private initPopupArea() {
      $('#ccg005-star-popup').ntsPopup({
        position: {
          my: 'left top',
          at: 'left-100 bottom',
          of: $('#ccg005-star-img')
        },
        showOnStart: false,
        dismissible: true
      });
      $('#ccg005-star-img').click(() => $('#ccg005-star-popup').ntsPopup('toggle'));
    }

    initFocusA1_4() {
      $('.CCG005-A1_4-border')
        .focusin(() => $('.ccg005-clearbtn').css('visibility', 'visible'))
        .focusout(() => $('.ccg005-clearbtn').css('visibility', 'hidden'));
    }

    /**
     * Popup A1_7 & A4_7
     */
    private initPopupStatus() {
      const vm = this;
      $('#ccg005-status-popup').ntsPopup({
        position: { my: 'right top', at: 'left top', of: $('.ccg005-status-img-A1_7') },
        showOnStart: false,
        dismissible: true
      });
      _.forEach($('.ccg005-status-img'), (element, index) => {
        $(element).click(() => {
          if (!vm.isBaseDate()) {
            return;
          }
          $('#ccg005-status-popup').ntsPopup({
            position: { my: 'left top', at: 'right top', of: $(element) },
            showOnStart: false,
            dismissible: true
          });
          // vm.activatedStatus();
          $('#ccg005-status-popup').ntsPopup('toggle');
        });
      });
      $('.ccg005-status-img-A1_7').click(() => {
        if (!vm.isBaseDate()) {
          return;
        }
        vm.goOutParams(new GoOutParam({
          sid: __viewContext.user.employeeId,
          businessName: vm.businessName(),
          goOutDate: moment.utc().format("YYYY/MM/DD")
        }));
        vm.activatedStatus(vm.activityStatus());
        $('#ccg005-status-popup').ntsPopup({
          position: { my: 'right top', at: 'left top', of: $('.ccg005-status-img-A1_7') },
          showOnStart: false,
          dismissible: true
        });
        $('#ccg005-status-popup').ntsPopup('toggle');
      });
    }

    nextPage() {
      const vm = this;
      if (vm.currentPage() * vm.perPage() < vm.totalElement()) {
        vm.currentPage(vm.currentPage() + 1);
      }
    }

    previousPage() {
      const vm = this;
      if (vm.currentPage() > 1) {
        vm.currentPage(vm.currentPage() - 1);
      }
    }

    submit() { }

    openScreenCCG005B() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/b/index.xhtml');
    }

    /**
     * A3_2.1をクリックする（お気に入りダイアログを起動する）
     */
    openScreenCCG005D() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/d/index.xhtml');
    }

    /**
     * A1_7.3をクリックする　OR　外出アイコンをクリックする（外出入力ダイアログを起動する）
     */
    openScreenCCG005E() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/e/index.xhtml', vm.goOutParams());
    }

    private toStartScreen() {
      const vm = this;
      const loginSid = __viewContext.user.employeeId;
      vm.$blockui('show');
      vm.$ajax('com', API.getDisplayAttendanceData).then((response: object.DisplayAttendanceDataDto) => {
        // A1_2 表示初期の在席データDTO.自分のビジネスネーム
        vm.businessName(response.bussinessName);
        vm.favoriteSpecifyData(response.favoriteSpecifyDto);
        vm.emojiUsage(!!response.emojiUsage);
        vm.inCharge(response.inCharge);
        if (response && response.attendanceInformationDtos) {
          // 条件：在席情報DTO.社員ID＝ログイン社員ID
          const atdInfo = _.find(response.attendanceInformationDtos, item => item.sid === loginSid);
          if (!atdInfo) {
            return;
          }
          // A1_1 表示初期の在席データDTO.在席情報DTO.個人の顔写真.顔写真ファイルID
          if (atdInfo.avatarDto && atdInfo.avatarDto.fileId) {
            $(`#${ID_AVATAR_CHANGE}`)
              .append($("<img/>")
                .attr("id", 'CCG005_A1_1')
                .attr("src", (nts.uk.request as any).liveView(atdInfo.avatarDto.fileId))
              );
          } else {
            $(`#${ID_AVATAR_CHANGE}`).ready(() => {
              $(`#${ID_AVATAR_CHANGE}`).append(
                `<div id='CCG005_no_avatar'>
                  <p style="text-align: center; margin: 0 auto; font-size: 15px">
                    ${vm.businessName().replace(/\s/g, '').substring(0, 2)}
                  </p>
                </div>`
              );
            });
          }
          // 表示初期の在席データDTO.在席情報DTO.在席のステータス
          vm.activityStatus(atdInfo.activityStatusDto);
          // A1_3
          if (atdInfo.emojiDto) {
            // 表示初期の在席データDTO.在席情報DTO.社員の外出情報.感情種類
            vm.emoji(vm.initEmojiType(atdInfo.emojiDto.emojiType));
          }
          // A1_4
          if (atdInfo.commentDto) {
            vm.comment(atdInfo.commentDto.comment);
            vm.commentDate(atdInfo.commentDto.date);
          }

          if (_.isEmpty(vm.favoriteSpecifyData())) {
            vm.createdDefaultFavorite();
          }
        }
      }).always(() => vm.$blockui('clear'));
    }

    private initEmojiType(emojiType: number): number {
      switch (emojiType) {
        case EmojiType.WEARY: return Emoji.WEARY;
        case EmojiType.SAD: return Emoji.SAD;
        case EmojiType.AVERAGE: return Emoji.AVERAGE;
        case EmojiType.GOOD: return Emoji.GOOD;
        case EmojiType.HAPPY: return Emoji.HAPPY;
        default: return Emoji.AVERAGE;
      }
    }

    private initActivityStatus(status: number): number {
      switch (status) {
        case StatusClassfication.NOT_PRESENT: return StatusClassficationIcon.NOT_PRESENT;
        case StatusClassfication.PRESENT: return StatusClassficationIcon.PRESENT;
        case StatusClassfication.GO_OUT: return StatusClassficationIcon.GO_OUT;
        case StatusClassfication.GO_HOME: return StatusClassficationIcon.GO_HOME;
        case StatusClassfication.HOLIDAY: return StatusClassficationIcon.HOLIDAY;
        default: return StatusClassficationIcon.NOT_PRESENT;
      }
    }

    private createdDefaultFavorite() {
      const vm = this;
      const inputDate = moment.utc().toISOString();
      const favoriteSpecify = new FavoriteSpecifyData({
        favoriteName: vm.$i18n('CCG005_27'),
        creatorId: __viewContext.user.employeeId,
        inputDate: inputDate,
        targetSelection: 1,
        workplaceId: [],
        order: 0,
        wkspNames: []
      });
      vm.favoriteSpecifyData([favoriteSpecify]);
      vm.favoriteInputDate(inputDate);
      vm.$blockui('show');
      vm.$ajax(API.saveFavorite, [favoriteSpecify])
        .always(() => vm.$blockui('clear'));
    }

    /**
     * コメントを登録する
     */
    registerComment() {
      const vm = this;
      const command = {
        comment: vm.comment(),
        date: moment.utc().toISOString(),
        sid: __viewContext.user.employeeId
      };
      vm.$blockui('show');
      vm.$ajax('com', API.registerComment, command)
        .then(() => vm.$dialog.info({ messageId: 'Msg_15' }))
        .always(() => vm.$blockui('clear'));
    }

    /**
     * コメントを削除する
     */
    deleteComment() {
      const vm = this;
      $('.ccg005-clearbtn').css('visibility', 'hidden');
      $('#CCG005-A1_4').focusout();
      vm.comment('');
      const command = {
        date: moment.utc(vm.commentDate()).toISOString(),
        sid: __viewContext.user.employeeId
      };
      vm.$blockui('show');
      vm.$ajax('com', API.deleteComment, command).always(() => vm.$blockui('clear'));
    }

    /**
     * 顔写真をクリックする（CDL010を起動する）
     */
    onClickAvatar() {

    }

    /**
     * A3_2.3職場選択ボタンをクリックする　（職場：CDL008へ）
     */
    openCDL008() {
      const vm = this;
      const inputCDL008: any = {
        startMode: StartMode.WORKPLACE,
        isMultiple: true,
        showNoSelection: false,
        selectedCodes: vm.workplaceFromCDL008(),
        isShowBaseDate: true,
        baseDate: moment.utc().toISOString(),
        selectedSystemType: SystemType.EMPLOYMENT,
        isrestrictionOfReferenceRange: false
      };
      setShared('inputCDL008', inputCDL008);
      
      vm.$window.modal('/view/cdl/008/a/index.xhtml').then(() => {
        if (getShared('CDL008Cancel')) {
          setShared('CDL008Cancel', null);
          return;
        }
        const workplaceInfor = getShared('workplaceInfor');
        vm.workplaceNameFromCDL008(_.map(workplaceInfor, (wkp: any) => wkp.displayName).join('、'));
        // 職場を選択する時
        vm.workplaceFromCDL008(getShared('outputCDL008'));
        const param: DisplayInfoAfterSelectParam = new DisplayInfoAfterSelectParam({
          baseDate: vm.selectedDate(),
          emojiUsage: vm.emojiUsage(),
          wkspIds: vm.workplaceFromCDL008()
        });
        vm.$blockui('show');
        vm.$ajax('com', API.getDisplayInfoAfterSelect, param).then((res: object.DisplayInformationDto) => {
          if (!res) {
            return;
          }
          vm.attendanceInformationDtos(res.attendanceInformationDtos);
          vm.listPersonalInfo(res.listPersonalInfo);
        }).always(() => vm.$blockui('clear'));
      });
    }

    /**
     * 在席のステータスを登録する
     */
    registerAttendanceStatus(selectedStatus: number, sid?: string) {
      if (!!sid) {
        sid = __viewContext.user.employeeId;
      }


    }
  }

  enum StartMode {
    WORKPLACE = 0,
    DEPARTMENT = 1
  }

  enum SystemType {
    PERSONAL_INFORMATION = 1,
    EMPLOYMENT = 2,
    SALARY = 3,
    HUMAN_RESOURCES = 4,
    ADMINISTRATOR = 5
  }

  enum EmojiType {
    WEARY = 0, // どんより: アイコン#189
    SAD = 1, // ゆううつ: アイコン#188
    AVERAGE = 2, // 普通: アイコン#187
    GOOD = 3, // ぼちぼち: アイコン#186
    HAPPY = 4 // いい感じ: アイコン#185
  }

  enum Emoji {
    WEARY = 189, // どんより: アイコン#189
    SAD = 188, // ゆううつ: アイコン#188
    AVERAGE = 187, // 普通: アイコン#187
    GOOD = 186, // ぼちぼち: アイコン#186
    HAPPY = 185 // いい感じ: アイコン#185
  }

  enum StatusClassfication {
    NOT_PRESENT = 0, // 未出社: アイコン#196
    PRESENT = 1, // 在席: アイコン#195
    GO_OUT = 2, // 外出: アイコン#191
    GO_HOME = 3, // 帰宅: アイコン#196
    HOLIDAY = 4 // 休み: アイコン#197
  }

  enum StatusClassficationIcon {
    NOT_PRESENT = 196, // 未出社: アイコン#196
    PRESENT = 195, // 在席: アイコン#195
    GO_OUT = 191, // 外出: アイコン#191
    GO_HOME = 196, // 帰宅: アイコン#196
    HOLIDAY = 197 // 休み: アイコン#197
  }

  class FavoriteSpecifyData {
    favoriteName: string;
    creatorId: string;
    inputDate: string;
    targetSelection: number;
    workplaceId: string[];
    order: number;
    wkspNames: string[];

    constructor(init?: Partial<FavoriteSpecifyData>) {
      $.extend(this, init);
    }
  }

  class DisplayInfoAfterSelectParam {
    wkspIds: string[];
    baseDate: any;
    emojiUsage: boolean;

    constructor(init?: DisplayInfoAfterSelectParam) {
      $.extend(this, init);
    }
  }

  class GoOutParam {
    sid: string;
    businessName: string;
    goOutDate: string;

    constructor(init?: Partial<GoOutParam>) {
      $.extend(this, init);
    }
  }

  class AttendanceInformationViewModel {
    applicationDtos: any[];                                 //申請
    sid: string;                                            //社員ID
    attendanceDetailDto: AttendanceDetailViewModel;                               //詳細出退勤
    avatarDto: object.UserAvatarDto;                               //個人の顔写真
    activityStatusDto: number;                              //在席のステータス
    commentDto: any;                                        //社員のコメント情報
    goOutDto: GoOutEmployeeInformationViewModel;                  //社員の外出情報
    emojiDto: object.EmployeeEmojiStateDto;                    //社員の感情状態
    businessName: string;

    constructor(init?: Partial<AttendanceInformationViewModel>) {
      $.extend(this, init);
    }
  }

  class GoOutEmployeeInformationViewModel {
    goOutPeriod: string;
    goOutReason: string;
    constructor(init?: Partial<GoOutEmployeeInformationViewModel>) {
      $.extend(this, init);
    }
  }

  class AttendanceDetailViewModel {
    // 勤務の色
    workColor: number;

    // 勤務名
    workName: string;

    // 終了の色
    checkOutColor: number;

    // 終了時刻 + 開始時刻
    checkInCheckOutTime: string;

    // 開始の色
    checkInColor: number;

    // 勤務区分
    workDivision: number;

    constructor(init?: Partial<AttendanceDetailViewModel>) {
      $.extend(this, init);
    }
  }
}
