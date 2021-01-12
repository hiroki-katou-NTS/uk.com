/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.ccg005.a.screenModel {

  @component({
    name: 'ccg005-component',
    template: `<div style="display: flex; position: relative; padding: 0 10px 0 10px; overflow-y: auto; height: 465px" id="ccg005-watching">
    <div id="ccg005-content">
      <div class="grade-header-top">
        <!-- A0 -->
        <span data-bind="i18n: 'CCG005_1'" class="ccg005-bold"></span>
        <!-- A1_5 -->
        <i data-bind="ntsIcon: {no: 5, width: 25, height: 25}, click: $component.openScreenCCG005B"></i>
        <!-- A1_6 -->
        <img width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/reload.png"/>
      </div>
      <div class="grade-header-center">
        <table>
          <tr>
            <td>
              <!-- A1_1 -->
              <img width="40px" height="40px" style="border-radius: 50%;" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
            </td>
            <td style="padding-left: 5px; width: 370px;">
              <!-- A1_2 -->
              <span class="ccg005-bold" data-bind="text: initPresenceData().businessName"></span>
              <div class="ccg005-flex">
                <!-- A1_3 -->
                <img width="20px" height="20px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
                <!-- A1_4 -->
                <span>bb</span>
              </div>
            </td>
            <td>
              <!-- A1_7 -->
              <img class="ccg005-status-img" width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
            </td>
          </tr>
        </table>
      </div>
      <div class="grade-header-bottom ccg005-flex" style="position: relative;">
        <!-- A2_3 -->
        <div data-bind="ntsDatePicker: {
          name: '#[CCG005_36]',
          value: date,
          dateFormat: 'YYYY/MM/DD',
          fiscalMonthsMode: true,
          showJumpButtons: true
        }"></div>
        <!-- A2_1 -->
        <button id="ccg005-legends" style="margin-left: 5px;" data-bind="ntsLegendButton: legendOptions"></button>
        <div style="right: 0; position: absolute; display: flex; align-items: center;">
          <!-- A3_2 -->
          <img id="ccg005-star-img" style="margin-right: 5px;" width="20px" height="20px" src="${__viewContext.rootPath}/view/ccg/005/resource/favorite.png"/>
          <!-- A3_1 -->
          <div data-bind="ntsComboBox: {
            options: itemList,
            width: '130px',
            visibleItemsCount: 5,
            value: selectedCode,
            optionsValue: 'code',
            optionsText: 'name',
            required: true,
            columns: [
              { prop: 'name' }
            ]}"></div>
        </div>
      </div>
      <div class="grade-body-top">
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
      <div class="grade-body-bottom">
        <table style="width: 100%; border-collapse: separate; border-spacing: 0 5px">
          <tr style="background-color: yellow; height: 50px;">
            <td style="padding-right: 5px; width: 30px; background-color: white;">
              <!-- A4_1 -->
              <img style="border-radius: 50%; border: 1px groove;" width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/>
            </td>
            <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset">
              <!-- A4_8 -->
              <div>text 1</div>
              <!-- A4_5 -->
              <div>emoji 1</div>
            </td>
            <td class="ccg005-w100 ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
              <div>
                <!-- A4_2 -->
                <span>text 2</span>
                <!-- A4_4 -->
                <img width="13px" height="13px" src="${__viewContext.rootPath}/view/ccg/005/resource/application.png" />
              </div>
              <!-- A4_3 -->
              <div>emoji 1</div>
            </td>
            <td class="ccg005-pl-5 ccg005-border-groove ccg005-right-unset ccg005-left-unset">
              <!-- A4_7 -->
              <span class="ccg005-flex"><img class="ccg005-status-img" width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/AVERAGE.png"/></span>
            </td>
            <td class="ccg005-pl-5 ccg005-border-groove ccg005-left-unset">
              <!-- A4_6 time -->
              <div>10:25 - 15:45</div>
              <!-- A4_6 text -->
              <div>急に用事があるの</div>
            </td>
          </tr>
        </table>
      </div>
      <div class="grade-bottom ccg005-flex" style="width: 100%; align-items: center; position: relative; margin-top: 10px; margin-bottom: 10px;">
        <table style="width: 100%;">
          <tr>
            <td>
              <div class="ccg005-pagination ccg005-flex" style="align-items: center;">
                <!-- A5_1 -->
                <img width="15px" class="ccg005-pagination-btn" height="20px" src="${__viewContext.rootPath}/view/ccg/005/resource/previous.png" data-bind="click: $component.previousPage"/>
                <!-- A5_2 -->
                <span style="white-space: nowrap;" data-bind="text: $component.paginationText()"></span>
                <!-- A5_3 -->
                <img width="15px" height="20px" class="ccg005-pagination-btn" src="${__viewContext.rootPath}/view/ccg/005/resource/next.png" data-bind="click: $component.nextPage"/>
              </div>
            </td>
            <td style="width: 100%">
              <div class="ccg005-switch-btn" style="float: right;">
                <!-- A5_4 -->
                <div class="cf" data-bind="ntsSwitchButton: {
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
    <div id="ccg005-star-popup" style="width: 130px;">
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
              enterkey: submit,
              option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
                textmode: 'text',
                width: '110px',
                placeholder: $component.placeholder()
              }))
            }"></input>
          </td>
        </tr>
        <tr>
          <td style="padding-top: 10px;">
            <!-- A3_2.3 -->
            <button data-bind="i18n: 'CCG005_32'"></button>
            <!-- A3_2.4 -->
            <span data-bind="text: label3_2"></span>
          </td>
        </tr>
      </table>
    </div>
    <!-- A1_7 & A4_7 Popup -->
    <div id="ccg005-status-popup">
      <table>
        <tr>
          <td>
            <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
          </td>
          <!-- A1_7.1 -->
          <td>
            <img width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/stt_notpresent.png" />
          </td>
          <td data-bind="i18n: 'CCG005_43'"></td>
        </tr>
        <tr>
          <td>
            <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
          </td>
          <!-- A1_7.2 -->
          <td>
            <img width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/stt_present.png" />
          </td>
          <td data-bind="i18n: 'CCG005_44'"></td>
        </tr>
        <tr data-bind="click: $component.openScreenCCG005E">
          <td>
            <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
          </td>
          <!-- A1_7.3 -->
          <td>
            <img width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/stt_goout.png" />
          </td>
          <td data-bind="i18n: 'CCG005_39'"></td>
        </tr>
        <tr>
          <td>
            <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
          </td>
          <!-- A1_7.4 -->
          <td>
            <img width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/stt_gohome.png" />
          </td>
          <td data-bind="i18n: 'CCG005_44'"></td>
        </tr>
        <tr>
          <td>
            <i data-bind="ntsIcon: {no: 78, width: 15, height: 25}"></i>
          </td>
          <!-- A1_7.5 -->
          <td>
            <img width="25px" height="25px" src="${__viewContext.rootPath}/view/ccg/005/resource/stt_holiday.png" />
          </td>
          <td data-bind="i18n: 'CCG005_40'"></td>
        </tr>
      </table>
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
      padding: 5px;
      width: calc(100% - 5px);
      border: 1px groove black;
    }
    #ccg005-status-popup table tr,
    #ccg005-content i, #ccg005-content img {
      cursor: pointer;
    }
  </style>`
  })
  export class ViewModel extends ko.ViewModel {
    date: KnockoutObservable<string> = ko.observable('20000101');
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
    selectedCode: KnockoutObservable<string> = ko.observable('1');
    searchValue: KnockoutObservable<string> = ko.observable('');
    label3_2: KnockoutObservable<string> = ko.observable('label3_2');
    itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([
      new ItemModel('1', '基本給'),
      new ItemModel('2', '役職手当'),
      new ItemModel('3', '基本給ながい文字列ながい文字列ながい文字列')
    ]);
    initPresenceData: KnockoutObservable<InitPresenceData>;

    placeholder: KnockoutObservable<string> = ko.observable(this.$i18n('CCG005_45'));
    height: KnockoutObservable<number> = ko.observable(465);
    // Pagination
    currentPage: KnockoutObservable<number> = ko.observable(1);
    totalRow: KnockoutObservable<number> = ko.observable(7);
    perPage: KnockoutObservable<number> = ko.observable(7);
    totalElement: KnockoutObservable<number> = ko.observable(101);
    paginationText: KnockoutComputed<string> = ko.computed(() => {
      const vm = this;
      const start = vm.perPage() * (vm.currentPage() - 1) + 1;
      const end = start + vm.totalRow();
      return `${start}-${end}/${vm.totalElement()}`;
    })

    created() {
      const vm = this;
      const favoriteSpecify: FavoriteSpecify = {
        creatorId: 'mock-creatorId',
        favoriteName: 'mock-favoriteName',
        inputDate: 'mock-inputDate',
        order: 1,
        targetSelection: 1,
        workplaceId: ['mock-workplaceId']
      };
      const attendanceInfor: AttendanceInfor = {
        activityStatus: 1,
        applicationInfor: 1,
        attendance: 1,
        employeeCommentInfo: 1,
        employeeEmojiState: 1,
        employeeId: 'mock-employeeId',
        goOutEmployeeInfo: 1,
        userAvatar: 1
      };
      const initPresenceData: InitPresenceData = {
        attendanceInfor: attendanceInfor,
        businessName: 'Duli Đàm',
        favoriteSpecify: favoriteSpecify
      }
      vm.initPresenceData = ko.observable(initPresenceData);
    }

    mounted() {
      const vm = this;
      $('#ccg005-legends').click(() => {
        $('.legend-item-symbol').first().css('border', '1px groove');
      });
      vm.initResizeable();
      vm.initPopupArea();
      vm.initPopupStatus();
    }

    initResizeable() {
      $(window).on('ccg005.resize', () => {
        const subHeight = $('#ccg005-content').height()
          - $('.grade-header-top').height()
          - $('.grade-header-center').height()
          - $('.grade-header-bottom').height()
          - $('.grade-body-top').height()
          - $('.grade-bottom').height();
        _.floor(subHeight/55);
      });
    }

    /**
     * Popup A3_2
     */
    private initPopupArea() {
      $('#ccg005-star-popup').ntsPopup({
        position: {
            my: 'left top',
            at: 'right top',
            of: $('#ccg005-star-img')
        },
        showOnStart: false,
        dismissible: true
      });
      $('#ccg005-star-img').click(() => $('#ccg005-star-popup').ntsPopup('toggle'));
    }

    /**
     * Popup A1_7 & A4_7
     */
    private initPopupStatus() {
      _.forEach($('.ccg005-status-img'), element => {
        $('#ccg005-status-popup').ntsPopup({
          position: {
              my: 'left top',
              at: 'right top',
              of: element
          },
          showOnStart: false,
          dismissible: true
        });
        $(element).click(() => {
          $('#ccg005-status-popup').ntsPopup({
            position: {
                my: 'left top',
                at: 'right top',
                of: $(element)
            },
            showOnStart: false,
            dismissible: true
          });
          $('#ccg005-status-popup').ntsPopup('toggle');
        });
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

    submit() {}

    openScreenCCG005B() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/b/index.xhtml', {});
    }

    openScreenCCG005D() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/d/index.xhtml', {});
    }

    openScreenCCG005E() {
      const vm = this;
      vm.$window.modal('/view/ccg/005/e/index.xhtml', {});
    }
  }

  class ItemModel {
    code: string;
    name: string;

    constructor(code: string, name: string) {
        this.code = code;
        this.name = name;
    }
  }

  class InitPresenceData {
    favoriteSpecify: FavoriteSpecify;
    attendanceInfor: AttendanceInfor;
    businessName: string;
  }

  class FavoriteSpecify {
	  favoriteName: string;
	  creatorId: string;
	  inputDate: any;
	  targetSelection: number;
	  workplaceId: string[];
	  order: number;
  }

  class AttendanceInfor {
    userAvatar: any;
    activityStatus: any;
    applicationInfor: any;
    employeeId: string;
    employeeCommentInfo: any;
    goOutEmployeeInfo: any;
    employeeEmojiState: any;
    attendance: any;
  }
}
