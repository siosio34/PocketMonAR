require 'test_helper'

class PocketmonsControllerTest < ActionController::TestCase
  setup do
    @pocketmon = pocketmons(:one)
  end

  test "should get index" do
    get :index
    assert_response :success
    assert_not_nil assigns(:pocketmons)
  end

  test "should get new" do
    get :new
    assert_response :success
  end

  test "should create pocketmon" do
    assert_difference('Pocketmon.count') do
      post :create, pocketmon: { code: @pocketmon.code, latitude: @pocketmon.latitude, level: @pocketmon.level, longitude: @pocketmon.longitude }
    end

    assert_redirected_to pocketmon_path(assigns(:pocketmon))
  end

  test "should show pocketmon" do
    get :show, id: @pocketmon
    assert_response :success
  end

  test "should get edit" do
    get :edit, id: @pocketmon
    assert_response :success
  end

  test "should update pocketmon" do
    patch :update, id: @pocketmon, pocketmon: { code: @pocketmon.code, latitude: @pocketmon.latitude, level: @pocketmon.level, longitude: @pocketmon.longitude }
    assert_redirected_to pocketmon_path(assigns(:pocketmon))
  end

  test "should destroy pocketmon" do
    assert_difference('Pocketmon.count', -1) do
      delete :destroy, id: @pocketmon
    end

    assert_redirected_to pocketmons_path
  end
end
