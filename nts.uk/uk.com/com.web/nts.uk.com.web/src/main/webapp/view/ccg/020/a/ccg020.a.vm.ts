/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg020.a {

  const API = {
    get10LastResults: "sys/portal/generalsearch/history/get-10-last-result",
    getByContent: "sys/portal/generalsearch/history/get-by-content",
    saveHistorySearch: 'sys/portal/generalsearch/history/save',
    removeHistorySearch: 'sys/portal/generalsearch/history/remove',
    getAvatar: 'ctx/bs/person/avatar/get',
    isDisplayWarning: 'ctx/sys/gateway/system/is-display-warning',
    isDisplayNewNotice: 'sys/portal/notice/is-new-notice',
    checkSearchManual: 'sys/portal/generalsearch/check-search-manual'
  };

  @component({
    name: 'ccg020-component',
    template: `<div id="search-bar" class="cf">
    <i id="search-icon" data-bind="ntsIcon: { no: 19, width: 30, height: 30 }, click: openPopupSearchCategory" class="img-icon"></i>
    <input id="search" autocomplete="off" data-bind="ntsTextEditor: {
      value: valueSearch,
      enterkey: submit,
      constraint: 'SearchContent',
      option: ko.mapping.fromJS(new nts.uk.ui.option.TextEditorOption({
        textmode: 'text',
        width: '162px',
        placeholder: searchPlaceholder
      }))
    },
    click: eventClickSearch" />
    <div id="popup-search-category">
      <div id="radio-search-category" data-bind="ntsRadioBoxGroup: {
        options: searchCategoryList,
        optionsValue: 'id',
        optionsText: 'name',
        value: searchCategory,
        enable: true
        }" class="ntsControl radio-wrapper reset-element" tabindex="0">
      </div>
    </div>
    <div id="popup-result"></div>
    <div id="popup-search"></div>
  </div>
  <div id="message" class="cf">
    <i class="img" id="warning-msg" data-bind="ntsIcon: { no: 163, width: 20, height: 20 }, click: addEventClickWarningBtn, visible: isDisplayWarningMsg"></i>
    <i class="img" id="notice-msg" data-bind="ntsIcon: { no: 164, width: 20, height: 20 }, click: addEventClickNoticeBtn"></i>
    <i class="img" id="new-notice-msg" data-bind="ntsIcon: { no: 165, width: 10, height: 10 }, visible: isDisplayNewNotice"></i>
  </div>`
  })
  export class CCG020Screen extends ko.ViewModel {
    treeMenu: KnockoutObservableArray<TreeMenu> = ko.observableArray([]);
    treeMenuResult: KnockoutObservableArray<TreeMenu> = ko.observableArray([]);
    dataDisplay: KnockoutObservableArray<any> = ko.observableArray([]);
    generalSearchHistory: KnockoutObservable<GeneralSearchHistoryCommand> = ko.observable(null);
    valueSearch: KnockoutObservable<string> = ko.observable('');
    searchPlaceholder: KnockoutObservable<string> = ko.observable();
    searchCategory: KnockoutObservable<number> = ko.observable(0);
    searchCategoryList: KnockoutObservableArray<any> = ko.observableArray([]);
    isDisplayWarningMsg: KnockoutObservable<boolean> = ko.observable(false);
    isDisplayNewNotice: KnockoutObservable<boolean> = ko.observable(false);
    avatarInfo: KnockoutObservable<AvatarDto> = ko.observable(null);

    created() {
      const vm = this;
      vm.checkCanSearchManual();
      vm.searchPlaceholder(vm.$i18n('CCG002_6'));
    }

    mounted() {
      const vm = this;
      vm.addSearchBar();
      vm.getListMenu();
      vm.isDisplayWarning();
      // vm.isDisplayNewNoticeFunc();
      vm.$nextTick(() => vm.getAvatar());
      $('#radio-search-category').on('click', () => {
        vm.searchPlaceholder(vm.searchCategory() === 0 ? vm.$i18n('CCG002_7') : vm.$i18n('CCG002_6'));
      });
    }

    private getAvatar() {
      const vm = this;
      const $userImage = $('#user-image');
      vm.$blockui('grayout');
      vm.$ajax(API.getAvatar)
        .then((data) => {
          vm.avatarInfo(data);
          if (vm.avatarInfo().fileId) {
            $('<img/>')
              .attr('id', 'img-avatar')
              .attr('src', (nts.uk.request as any).liveView(vm.avatarInfo().fileId))
              .appendTo($userImage);
            $userImage.removeClass('ui-icon ui-icon-person');
            const $icon = $('#user')
              .find('.user-settings')
              .find('.ui-icon-caret-1-s');
            $icon.attr('style', 'top: 7px;');
            $('#search-bar').attr('style', 'bottom: 2px; position: relative;');
          } else {
            $userImage.ready(() => {
              $('<div/>')
                .addClass('avatar')
                .attr('id', 'A4_1_no_avatar')
                .text($('#user-name').text().substring(0, 2))
                .appendTo($userImage);
            });
          }
        })
        .always(() => vm.$blockui('clear'));
    }

    private addEventClickNoticeBtn() {
      const $message = $('#message');
      $('<div/>')
        .attr('id', 'popup-message')
        .appendTo($message);
      $('#popup-message').ntsPopup({
        showOnStart: false,
        dismissible: true,
        position: {
          my: 'right top',
          at: 'right bottom',
          of: '#notice-msg'
        }
      });
      $('popup-message').append('#closure');
      // CCG003を起動する（パネルイメージで実行）
      $('#popup-message').ntsPopup('show');
    }

    private addEventClickWarningBtn() {
      nts.uk.ui.dialog.info(__viewContext.program.operationSetting.message);
    }

    /* Screen CCG002 */
    private addSearchBar() {
      $('#popup-result').ntsPopup({
        showOnStart: false,
        dismissible: true,
        position: {
          my: 'left top',
          at: 'left bottom',
          of: '#search'
        }
      });
      $('#popup-search').ntsPopup({
        showOnStart: false,
        dismissible: true,
        position: {
          my: 'left top',
          at: 'left bottom',
          of: '#search'
        }
      });
      $('#popup-search-category').ntsPopup({
        showOnStart: false,
        dismissible: true,
        position: {
          my: 'right top',
          at: 'right bottom',
          of: '#search-icon'
        }
      });

      $('#list-box').on('selectionChanging', (event: any) => {
        window.location.href = event.detail.url;
      });
    }

    private openPopupSearchCategory() {
      $('#popup-search-category').ntsPopup('show');
    }

    private getListMenu() {
      const vm = this;
      const menuSet = JSON.parse(sessionStorage.getItem('nts.uk.session.MENU_SET').value);
      let treeMenu = _.flatMap(
        menuSet,
        i => _.flatMap(
          i.menuBar,
          ii => _.flatMap(
            ii.titleMenu,
            iii => iii.treeMenu
          )
        )
      );
      treeMenu = _.uniqBy(treeMenu, 'code');
      _.forEach(treeMenu, (item: TreeMenu) => {
        item.name = item.displayName === item.defaultName
          ? item.displayName
          : `${item.displayName} (${item.defaultName})`;
      });
      vm.treeMenu(treeMenu);
    }

    private eventClickSearch() {
      const vm = this;
      vm.get10LastResults();
      $('#popup-search').ntsPopup('show');
    }

    submit() {
      const vm = this;
      $('#list-box').remove();
      $('#popup-search').ntsPopup('hide');
      vm.treeMenuResult([]);
      vm.$validate()
        .then((valid) => {
          if (!valid) {
            return;
          }
          if (vm.valueSearch() !== '') {
            vm.addHistoryResult();
            vm.treeMenuResult(vm.filterItems(vm.valueSearch(), vm.treeMenu()));
            const $tableResult = $('<div/>').attr('id', 'list-box');
            const list = vm.treeMenuResult();
            if (list.length > 0) {
              _.forEach(list, (item) => {
                const $ul = $('<a/>')
                  .addClass('result-search custom-limited-label')
                  .attr('href', item.url)
                  .text(item.name);
                $tableResult.append($ul);
              });
            } else {
              $tableResult.text(nts.uk.resource.getText('CCG002_9'));
            }
            $('#popup-result')
              .append($tableResult)
              .ntsPopup('show');
          }
        });
    }

    private addHistoryResult() {
      const vm = this;
      vm.$blockui('grayout');
      const command: GeneralSearchHistoryCommand = new GeneralSearchHistoryCommand({
        searchCategory: vm.searchCategory(),
        contents: vm.valueSearch()
      });
      vm.$ajax(API.saveHistorySearch, command)
        .then(() => vm.get10LastResults())
        .always(() => vm.$blockui('clear'));
    }

    private removeHistoryResult(command: GeneralSearchHistoryCommand) {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(API.removeHistorySearch, command)
        .then(() => vm.get10LastResults())
        .always(() => vm.$blockui('clear'));
    }

    private get10LastResults() {
      const vm = this;
      $('#list-box-search').remove();
      vm.$blockui('grayout');
      vm.$ajax(`${API.get10LastResults}/${vm.searchCategory()}`)
        .then((response) => {
          vm.dataDisplay(response);
          vm.displayResultSearchHistory();
        })
        .always(() => vm.$blockui('clear'));
    }

    private displayResultSearchHistory() {
      const vm = this;
      const $tableSearch = $('<ul/>').attr('id', 'list-box-search');
      const list = vm.dataDisplay();
      if (list.length > 0) {
        _.forEach(list, (item) => {
          const $iconClose = $('<i/>')
            .attr('id', item.contents)
            .addClass('icon icon-close hide-class')
            .attr('style', 'float: right;');
          const $textLi = $('<span/>')
            .addClass('text-li custom-limited-label')
            .text(item.contents);
          const $li = $('<li/>')
            .addClass('result-search-history')
            .append($textLi)
            .append($iconClose)
            .on('click', (event) => vm.selectItemSearch(item))
            .appendTo($tableSearch)
            .hover(() => {
              $(`#${item.contents}`).removeClass('hide-class');
            }, () => {
              $(`#${item.contents}`).addClass('hide-class');
            });
          $iconClose.hover(() => {
            $iconClose.on('click', (event) => vm.removeHistoryResult(item));
            $li.off('click');
          }, () => {
            $iconClose.off('click');
            $li.on('click', (event) => vm.selectItemSearch(item));
          });
        });
      } else {
        $tableSearch.text(nts.uk.resource.getText('CCG002_5'));
      }
      const $popup = $('#popup-search');
      $popup.append($tableSearch);
    }

    private selectItemSearch(data: any) {
      const vm = this;
      vm.valueSearch(data.contents);
      $('#popup-search').ntsPopup('hide');
      vm.submit();
    }

    private filterItems(query: any, list: any) {
      return _.filter(list, (el: any) => _.includes(_.lowerCase(el.name), _.lowerCase(query)));
    }

    private isDisplayWarning() {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(API.isDisplayWarning)
        .then((response) => {
          vm.$blockui('clear');
          vm.isDisplayWarningMsg(response);
        })
        .always(() => vm.$blockui('clear'));
    }

    private isDisplayNewNoticeFunc() {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(API.isDisplayNewNotice)
        .then((response) => {
          vm.$blockui('clear');
          vm.isDisplayNewNotice(response);
        })
        .always(() => vm.$blockui('clear'));
    }
  
    private checkCanSearchManual() {
      const vm = this;
      vm.$blockui('grayout');
      vm.$ajax(API.checkSearchManual)
        .then((response) => {
          vm.$blockui('clear');
          if (response) {
            vm.searchCategoryList([
              { id: 0, name: vm.$i18n('CCG002_2') },
              { id: 1, name: vm.$i18n('CCG002_3') }
            ]);
          } else {
            vm.searchCategoryList([{ id: 0, name: vm.$i18n('CCG002_2') }]);
          }
        })
        .always(() => vm.$blockui('clear'));
    }
  }

  export class MenuSet {
    companyId: string;
    webMenuCode: number;
    webMenuName: string;
    defaultMenu: number;
    menuBar: MenuBar[];
    constructor(init?: Partial<MenuSet>) {
      $.extend(this, init);
    }
  }

  export class MenuBar {
    menuBarId: string;
    code: number;
    displayOrder: number;
    link: string;
    menuBarName: string;
    menuCls: number;
    selectedAttr: number;
    system: number;
    textColor: string;
    titleMenu: TitleMenu[];
    constructor(init?: Partial<MenuBar>) {
      $.extend(this, init);
    }
  }

  export class TitleMenu {
    backgroundColor: string;
    displayOrder: number;
    imageFile: string;
    textColor: string;
    titleMenuAtr: number;
    titleMenuCode: string;
    titleMenuId: string;
    titleMenuName: string;
    treeMenu: TreeMenu[];
    constructor(init?: Partial<TitleMenu>) {
      $.extend(this, init);
    }
  }

  export class TreeMenu {
    afterLoginDisplay: number;
    classification: number;
    code: string;
    defaultName: string;
    displayName: string;
    displayOrder: number;
    menuAttr: number;
    programId: string;
    queryString: string;
    screenId: string;
    system: number;
    url: string;
    name: string;
    constructor(init?: Partial<TreeMenu>) {
      $.extend(this, init);
    }
  }

  export class GeneralSearchHistoryCommand {
    contents: string;
    searchCategory: number;
    constructor(init?: Partial<GeneralSearchHistoryCommand>) {
      $.extend(this, init);
    }
  }

  export class AvatarDto {
    /** 個人ID
    */
    personalId: string;
    /**
     * 顔写真ファイルID
     */
    fileId: string;
    constructor(init?: Partial<AvatarDto>) {
      $.extend(this, init);
    }
  }
}
