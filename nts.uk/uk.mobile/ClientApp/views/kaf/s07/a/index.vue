<template>
  <div class="kafs07a" v-bind="data">
    <!-- <div>
      <kafs00-a v-if="true" v-bind:params="{application: application}" />
    </div> -->

    <div v-if="!$valid" class="alert error">
      <img
        class="iconWarn"
        src="https://www.iconsdb.com/icons/preview/red/warning-xxl.png"
        width="16"
        height="16"
      />
      <div class="contentError">{{'KAFS07_1'| i18n}}</div>
    </div>
    <div>
      <!-- <kafs00-b
        v-if="true"
        v-bind:params="{
            application: application, 
            appDispInfoStartupOutput: appDispInfoStartupOutput
        }"
      /> -->
    </div>

    <div class="card card-label">
      <!-- A4_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{'KAFS07_2' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div class="card-body">
        <!-- A4_2 -->
        <span class="textSize uk-text-dark-gray">{{'KAFS07_3' | i18n}}</span>
        <button type="button" class="btn btn-selection mt-2 mb-2" v-on:click="openKDL002()">
          <!-- A4_2_1 -->
          <span class="badge badge-secondary ">{{worktype.code}}</span>
          <span>{{worktype.name}}</span>
        </button>

        <!-- A4_3 -->
        <span class="textSize uk-text-dark-gray">{{'KAFS07_4' | i18n}}</span>
        <button
          type="button"
          v-bind:enable="isCondition1"
          class="btn btn-selection mt-2 mb-2"
          v-on:click="openKDL002()"
        >
          <!-- A4_3_1 -->
          <span class="badge badge-secondary ">{{worktime.code}}</span>
          <span>{{worktime.name}}</span>
          <!-- A4_3_2 -->
          <span class="d-block mt-1">{{worktime.time}}</span>
        </button>
      </div>
    </div>

    <div class="card card-label">
      <!-- A5_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{'KAFS07_5' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div class="card-body">
        <nts-switchbox v-model="switchbox1" v-bind:value="1">{{'KAFS07_11' | i18n}}</nts-switchbox>
        <nts-switchbox v-model="switchbox1" v-bind:value="2">{{'KAFS07_12' | i18n}}</nts-switchbox>
      </div>
      <div class="card-body">
        <nts-switchbox v-model="switchbox2" v-bind:value="1">{{'KAFS07_13' | i18n}}</nts-switchbox>
        <nts-switchbox v-model="switchbox2" v-bind:value="2">{{'KAFS07_14' | i18n}}</nts-switchbox>
      </div>
    </div>

    <div class="card card-label">
      <!-- A6_1 -->
      <div v-if="isDisplay3()" class="card-header uk-bg-accordion">
        <span>{{'KAFS07_6' | i18n}}</span>
        <span class="badge badge-warning">必須</span>
      </div>
      <div v-if="isDisplay3()" v-bind:enable="isCondition1" class="card-body">
        <nts-time-range-input v-model="valueWorkHours1" />
      </div>
    </div>

    <div class="card card-label">
      <!-- A7_1 -->
      <div v-if="isDisplay2()" class="card-header uk-bg-accordion">
        <span>{{'KAFS07_7' | i18n}}</span>
        <span class="badge badge-info">必須</span>
      </div>
      <!-- A7_2 -->
      <div v-if="isDisplay2()" v-bind:enable="isDisplay3()" class="card-body">
        <nts-time-range-input v-model="valueWorkHours2" />
      </div>
    </div>
    <div>
      <!-- <kafs00-c
        v-if="true"
        v-bind:params="{
            application: application,
            appDispInfoStartupOutput: appDispInfoStartupOutput
        }"
      /> -->
    </div>
    <!-- display text by  ※1-->
    <!-- 画面モード = 新規モード -->
    <button
      v-if="mode == 'create'"
      type="button"
      class="btn btn-primary btn-block"
      v-on:click="register()"
    >{{'KAFS07_8' | i18n}}</button>
    <!-- 画面モード = 編集モード -->
    <button
      v-else
      type="button"
      class="btn btn-primary btn-block"
      v-on:click="register()"
    >{{'KAFS07_16' | i18n}}</button>
  </div>
</template>