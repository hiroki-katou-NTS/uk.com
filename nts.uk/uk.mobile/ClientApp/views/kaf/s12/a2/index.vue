<template>
  <div class="kafs12a2">
    <div
      v-for="calculateResultItem in calculateResultLst"
      v-bind:key="calculateResultItem.frame"
    >
      <!-- A_B1_1 -->
      <div class="card card-label">
        <div class="card-header uk-bg-accordion">
          <span>{{ calculateResultItem.workHeader | i18n }}</span>
          <span class="badge badge-info">任意</span>
        </div>
      </div>
      <div class="row mt-3 justify-content-around">
        <!-- A_B1_2 -->
        <div class="col-4 col1 p-2 text-center">
          <span>{{
            "KAFS12_21" | i18n(calculateResultItem.requiredTime)
          }}</span>
        </div>
        <!-- A_B1_3 -->
        <div class="col-4 col2 p-2 text-center">
          <span>{{
            "KAFS12_22" | i18n(calculateResultItem.applicationTime)
          }}</span>
        </div>
      </div>
      <div>
        <div
          v-for="appliesTimeItem in calculateResultItem.appliesTime"
          v-bind:key="appliesTimeItem.frame"
        >
          <!-- A_B1_4 -->
          <div class="position-relative">
            <nts-time-editor
              v-model="appliesTimeItem.hoursOfWorkType"
              v-bind:name="appliesTimeItem.title | i18n"
              time-input-type="time-duration"
              v-bind:show-title="true">
            </nts-time-editor>
            <span class="numberOfHoursLeft position-absolute">{{'KAFS12_23' | i18n}}</span>
          </div>
        </div>
      </div>
      <!-- A_B1_14 -->
      <nts-dropdown
        v-bind:name="'KAFS12_44' | i18n"
        v-model="hoursOfWorkTypeFromA1"
      >
        <option v-for="item in dropDownList" v-bind:key="item.code">
          {{ item.code }} &nbsp;&nbsp;&nbsp; {{ item.name }}
        </option>
      </nts-dropdown>
      <!-- A_B1_15 -->
      <div class="position-relative">
        <nts-time-editor
          v-model="hoursOfWorkTypeFromA12"
          v-bind:name="'KAFS12_44' | i18n"
          time-input-type="time-duration"
          v-bind:show-title="false">
        </nts-time-editor>
        <span class="numberOfHoursLeft2 position-absolute">{{'KAFS12_23' | i18n}}</span>
      </div>
    </div>
    <kafs00c
      v-if="kaf000_C_Params != null"
      v-bind:params="kaf000_C_Params"
      @kaf000CChangeReasonCD="kaf000CChangeReasonCD"
      @kaf000CChangeAppReason="kaf000CChangeAppReason">
    </kafs00c>
    <!-- A_B8_1 -->
    <button
        type="button"
        class="btn btn-primary btn-block text-center mb-3"
        v-on:click="nextToStep3()"
        v-if="mode"
      >
        {{ "KAFS12_31" | i18n }}
      </button>
      <button
        type="button"
        class="btn btn-primary btn-block text-center mb-3"
        v-on:click="nextToStep3()"
        v-if="!mode"
      >
        {{ "KAFS12_32" | i18n }}
      </button>
  </div>
</template>