<template>
  <div>
    <div
      id="clock"
      v-bind:style="{
        'background-color':
          setting.displaySettingsStampScreen.settingDateTimeColor
            .backgroundColor,
        color: setting.displaySettingsStampScreen.settingDateTimeColor.textColor
      }"
    >
      <div class="col-12 pr-1 pt-2 font-weight-bold clearfix">
        <span id="date">
          {{ $dt.now | date("YYYY年 MM月 DD日（ddd）") }}
        </span>
        <button
          v-on:click="openDialogS"
          type="button"
          class="float-right btn btn-success"
        >
          {{ "KDPS01_25" | i18n }}
        </button>
      </div>
      <div class="col-12 px-5 font-weight-bold">
        <span id="time">{{ $dt.now | date("HH:mm") }}</span>
      </div>
    </div>
    <div>
      <pre
        class="pl-4 pt-2"
        style="min-height: 4rem;"
        v-bind:style="{
          color: setting.stampPageComment.commentColor
        }"
        >{{ setting.stampPageComment.pageComment }}</pre
      >
    </div>
    <div class="d-flex flex-wrap">
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
            'background-color': button.buttonDisSet.backGroundColor
          }"
          v-click:500="() => stampClick(button)"
          class="col-12 btn-stamp btn btn-secondary "
        >
          {{ button.buttonDisSet.buttonNameSet.buttonName }}
        </button>
      </div>
    </div>
  </div>
</template>
