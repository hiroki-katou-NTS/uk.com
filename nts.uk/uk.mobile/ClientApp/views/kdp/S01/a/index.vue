<template>
  <div class="mx-n3">
    <div
      class="pl-3 text-break"
      v-bind:style="{
        color: setting.stampPageComment.commentColor
      }"
      v-html="textComment"
    ></div>
    <div
      class="pl-3 pt-2 pr-2"
    >
      <div class="clearfix" v-bind:style="{color: setting.displaySettingsStampScreen.settingDateTimeColor.textColor}">
        <span id="date">
          {{ $dt.now | date("YYYY年 MM月 DD日（ddd）") }}
        </span>
        <div>
          <span id="time">{{ $dt.now | date("HH:mm") }}</span>
          <span class="second">:{{ $dt.now | date("ss") }}</span>
              <div
          v-on:click="openDialogS"
          type="text"
          class="blue-link"
        >
          {{ "KDPS01_25" | i18n }}
        </div>
        </div>
      </div>
    </div>

    <div class="px-3">
      <div class="row">
        <div
          class="col-6 mb-2 px-2 div-btn-stamp"
          v-for="button in setting.buttons"
          v-bind:key="button.buttonPositionNo"
        >
          <button
            type="button"
            v-if="button.buttonDisSet.backGroundColor !== ''"
            v-bind:style="{
              color: button.buttonDisSet.buttonNameSet.textColor,
              'background-color': button.buttonDisSet.displayBackGroundColor
            }"
            v-click:500="() => stampClick(button)"
            class="btn-block btn-stamp btn btn-secondary "
          >
            <img v-bind:src="'/nts.uk.mobile.web/dist/resources/' + button.icon" height="80" width="80"/> 
            <br>
            {{ button.buttonDisSet.buttonNameSet.buttonName }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
