<template>
  <div class="kafs12a1">
    <div>
      <kafs00-a
        v-if="kaf000_A_Params != null"
        v-bind:params="kaf000_A_Params"
      />
    </div>
    <!-- A_A3_1 -->
    <div
      v-if="!$valid || !isValidateAll"
      class="card bg-danger top-alert uk-text-danger topError"
    >
      <button class="btn btn-link uk-text-danger">
        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
        {{ "KAFS08_13" | i18n }}
      </button>
    </div>
    <div>
      <kafs00-b
        v-if="kaf000_B_Params != null"
        v-bind:params="kaf000_B_Params"
        @kaf000BChangeDate="kaf000BChangeDate"
        @kaf000BChangePrePost="kaf000BChangePrePost"
      />
    </div>
    <!-- A_A5_1 -->
    <div
      v-for="dispInfoTimeLeaveRequest in DispInfoOfTimeLeaveRequestLst"
      v-bind:key="dispInfoTimeLeaveRequest.frame"
    >
      <div class="card card-label">
        <div class="card-header uk-bg-accordion">
          <span>{{ dispInfoTimeLeaveRequest.header | i18n }}</span>
          <span class="badge badge-info">任意</span>
        </div>
      </div>
      <!-- A_A5_2 -->
      <span class="uk-text-dark-gray ml-1">{{
        dispInfoTimeLeaveRequest.attendanceTimeLabel | i18n
      }}</span>
      <!-- A_A5_3 -->
      <div class="position-relative">
        <nts-time-editor
          v-model="dispInfoTimeLeaveRequest.attendanceTime"
          v-bind:name="dispInfoTimeLeaveRequest.titleOfAttendanceTime | i18n"
          time-input-type="time-with-day"
          v-bind:show-title="true"
        />
        <div class="kafs00p1">
          <div class="position-absolute">
            <kafs00-p1
              v-bind:params="dispInfoTimeLeaveRequest.kafS00P1Params"
            />
          </div>
        </div>
      </div>
    </div>
    <!-- A_A9_1 -->
    <div class="card card-label">
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS12_13" | i18n }}</span>
        <span class="badge badge-info">任意</span>
      </div>
      <!-- A_A9_2 -->
      <span class="uk-text-dark-gray ml-1">{{ "KAFS12_14" | i18n }}</span>
      <!-- A_A9_3 -->
      <div>
        <div
          v-for="goBackTimeItem in GoBackTimeLst"
          v-bind:key="goBackTimeItem.frame"
        >
          <div class="card-body mb-n3">
            <nts-time-range-input
              v-model="goBackTimeItem.goBackTime"
              v-bind:showTile="true"
              v-bind:name="goBackTimeItem.name | i18n(goBackTimeItem.frame + 1)"
            >
            </nts-time-range-input>
            <div class="card-body w-100 mt-n3">
              <nts-switchbox
                v-for="option in dataSource"
                v-bind:key="option.id"
                v-model="goBackTimeItem.swtOutClassification"
                v-bind:value="option.id"
                >{{ option.name | i18n }}
              </nts-switchbox>
            </div>
          </div>
        </div>
      </div>
      <div class="mb-2">
        <div class="text-center position-relative" style="height: 35px">
          <!-- A_A9_6 -->
          <div class="position-absolute w-100">
            <hr />
          </div>
          <!-- A_A9_5 -->
          <div class="position-absolute w-100 mt-2">
            <span class="fas fa-2x fa-plus-circle" v-on:click="addNewGoBackTime()"></span>
          </div>
        </div>
        <!-- A_A9_7 -->
        <div class="text-center">{{ "KAFS12_17" | i18n }}</div>
      </div>
      <!-- A_A10_1 -->
      <button
        type="button"
        class="btn btn-success btn-block text-center mb-3"
        v-on:click="nextToStep2()"
        v-if="mode"
      >
        {{ "KAFS12_19" | i18n }}
      </button>
      <button
        type="button"
        class="btn btn-primary btn-block text-center mb-3"
        v-on:click="nextToStep2()"
        v-if="!mode"
      >
        {{ "KAFS12_19" | i18n }}
      </button>
    </div>
  </div>
</template>