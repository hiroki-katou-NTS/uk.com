<template>
  <div class="kafs04a">
    <kaf-s00-a class="mx-n2"
    v-if="kafS00AParams" 
    v-bind:params="kafS00AParams" />
    <!-- error message -->
    <div
      v-if="!validAll"
      class="card bg-danger top-alert uk-text-danger topError">
      <button class="btn btn-link uk-text-danger">
        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
        {{ "KAFS07_1" | i18n }}
      </button>
    </div>
    <div v-else></div>
    <kaf-s00-b 
    v-if="kafS00BParams"
    @kaf000BChangeDate="handleChangeDate"
    @kaf000BChangePrePost="handleChangePrePost"
    v-bind:params="kafS00BParams" />
    <!-- A3 -->
      <div class="position-relative">
        <div class="card card-label py-3">
          <div class="card-header uk-bg-accordion">
            <span>{{ "KAFS04_2" | i18n }}</span>
            <span class="badge badge-warning">必須</span>
          </div>
        </div>
      <kaf-s00-p1 
      v-bind:params="kafS00P1Params1"
      class="position-absolute right ab1" />
      <!-- A3_1 -->
      <nts-time-editor
        class="border-left-0"
        v-model="time.attendanceTime"
        :name="'KAFS04_3'"
        time-input-type="time-with-day"
      />
      <!-- A3_3 -->
      <nts-checkbox
          v-model="check.cbCancelLate.value"
          v-bind:disabled="check.cbCancelLate.isDisable"
          v-if="showCheckBox"
          v-bind:value="'Attendance'">{{'KAFS04_5' | i18n}}
        </nts-checkbox>
      <template v-else />
      <div class="position-relative">
        <kaf-s00-p1 
      v-bind:params="kafS00P1Params2"
      class="position-absolute right ab2" />
        <!-- A3_4 -->
        <nts-time-editor
          v-model="time.leaveTime"
          :name="'KAFS04_6'"
          time-input-type="time-with-day"
        />
        <!-- A3_6 -->
        <nts-checkbox
          v-model="check.cbCancelEarlyLeave.value"
          v-if="showCheckBox"
          v-bind:disabled="check.cbCancelEarlyLeave.isDisable"
          v-bind:value="'Early'">{{'KAFS04_7' | i18n}}
        </nts-checkbox>
        <template v-else />
      </div>
      </div>
    <!-- A4 -->
    <div class="position-relative" v-if="conditionLateEarlyLeave2Show">
      <div class="card card-label py-3">
        <div class="card-header uk-bg-accordion">
          <span>{{ "KAFS04_8" | i18n }}</span>
          <span class="badge badge-info">任意</span>
        </div>
      </div>
      <kaf-s00-p1 
      v-bind:params="kafS00P1Params3"
      class="position-absolute right ab3" />
      <!-- A4_1 -->
      <nts-time-editor
        class="border-left-0"
        v-model="time.attendanceTime2"
        :name="'KAFS04_9'"
        time-input-type="time-with-day"
      />
      <!-- A4_3 -->
      <nts-checkbox
        v-model="check.cbCancelLate2.value"
        v-if="showCheckBox"
        v-bind:disabled="check.cbCancelLate2.isDisable"
        v-bind:value="'Attendance2'">{{'KAFS04_10' | i18n}}
      </nts-checkbox>
      <template v-else />
      <div class="position-relative">
        <kaf-s00-p1 
      v-bind:params="kafS00P1Params4"
      class="position-absolute right ab4" />
        <!-- A4_4 -->
        <nts-time-editor
          v-model="time.leaveTime2"
          :name="'KAFS04_11'"
          time-input-type="time-with-day"
        />
        <!-- A4_6 -->
        <nts-checkbox
          v-model="check.cbCancelEarlyLeave2.value"
          v-if="showCheckBox"
          v-bind:disabled="check.cbCancelEarlyLeave2.isDisable"
          v-bind:value="'Early2'">{{'KAFS04_12' | i18n}}
        </nts-checkbox>
        <template v-else />
      </div>
    </div>
    <kaf-s00-c class="py-3"
    v-if="kafS00CParams != null"
    @kaf000CChangeAppReason="handleChangeAppReason"
    @kaf000CChangeReasonCD="handleChangeReasonCD"
    v-bind:params="kafS00CParams" />
   <button
        v-if="mode"
        type="button"
        class="btn btn-primary btn-block"
        v-on:click="checkBeforeRegister()"
      >{{'KAFS04_13' | i18n}}</button>
      <!-- 画面モード = 編集モード -->
      <button
        v-else
        type="button"
        class="btn btn-primary btn-block"
        v-on:click="checkBeforeRegister()"
      >{{'KAFS04_4' | i18n}}</button>
  </div>
</template>