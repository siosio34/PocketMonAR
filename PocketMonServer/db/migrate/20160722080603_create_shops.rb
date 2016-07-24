class CreateShops < ActiveRecord::Migration
  def change
    create_table :shops do |t|
      t.float :latitude
      t.float :longitude
      t.string :shoptag
      t.string :description

      t.timestamps null: false
    end
  end
end
