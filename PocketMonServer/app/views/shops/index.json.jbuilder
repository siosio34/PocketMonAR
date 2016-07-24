json.array!(@shops) do |shop|
  json.extract! shop, :id, :latitude, :longitude, :shoptag, :description
  json.url shop_url(shop, format: :json)
end
